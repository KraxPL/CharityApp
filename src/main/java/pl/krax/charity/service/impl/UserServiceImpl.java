package pl.krax.charity.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.krax.charity.dto.UserDto;
import pl.krax.charity.dto.UserRegisterDto;
import pl.krax.charity.entities.Role;
import pl.krax.charity.entities.User;
import pl.krax.charity.mapper.UserMapper;
import pl.krax.charity.repository.RoleRepository;
import pl.krax.charity.repository.UserRepository;
import pl.krax.charity.service.MailSenderService;
import pl.krax.charity.service.UserService;
import pl.krax.charity.token.TokenGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final String DEFAULT_PASSWORD = "password";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";
    private static final String USER_ROLE = "ROLE_USER";
    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;
    private final TokenGenerator tokenGenerator;
    @Override
    @Transactional
    public void save(UserRegisterDto userRegisterDto) {
        User user = userMapper.toUser(userRegisterDto, roleRepository, passwordEncoder);
        String token = tokenGenerator.generateToken();
        user.setActivationToken(token);
        userRepo.save(user);
        sendConfirmationEmail(userRegisterDto, token);
    }

    @Override
    @Transactional
    public Optional<User> findById(Long userId) {
        return userRepo.findById(userId);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    @Transactional
    public void update(UserDto userDto) {
        User existingUser = userRepo.findById(userDto.getId())
                .orElse(null);
        if (existingUser != null) {
            User user = userMapper.toEntity(userDto);
            user.setRoles(existingUser.getRoles());
            user.setActiveAccount(existingUser.getActiveAccount());
            user.setId(existingUser.getId());
            user.setPassword(existingUser.getPassword());
            userRepo.save(user);
        }
    }

    @Override
    @Transactional
    public User findByUserEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public UserDto findUserDtoByUserEmail(String email) {
        return userMapper.toDto(
                userRepo.findByEmail(email));
    }
    @Override
    public boolean checkPasswordAndCurrentPassword(String newPassword, String confirmPassword,
                                                   String currentPassword, Long userId) {
        return checkPasswordRepeat(newPassword, confirmPassword) &&
                checkCurrentPassword(currentPassword, userId);
    }

    @Override
    @Transactional
    public List<UserDto> findAllAdmins() {
        return userRepo.findAll().stream()
                .filter(this::isAdmin)
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean delete(Long userId) {
        return findById(userId)
                .filter(user -> isAdmin(user) && countAdmins() > 1 || !isAdmin(user))
                .map(user -> {
                    userRepo.delete(user);
                    return true;
                })
                .orElse(false);
    }

    @Override
    @Transactional
    public void createAdmin(UserDto userDto) {
        createUserOrAdmin(userDto, true);
    }

    @Override
    @Transactional
    public UserDto findUserDtoById(Long id) {
        return findById(id).map(userMapper::toDto)
                .orElse(null);
    }

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        createUserOrAdmin(userDto, false);
    }

    @Override
    @Transactional
    public void createUserOrAdmin(UserDto userDto, boolean isAdmin) {
        User user = UserMapper.INSTANCE.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        user.setActiveAccount(isAdmin ? 1 : 0);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByRoleName(isAdmin ? ADMIN_ROLE : USER_ROLE));
        user.setRoles(roles);
        userRepo.save(user);
    }

    @Override
    @Transactional
    public void lockOrUnlockUser(Long userId) {
        Optional<User> userOptional = findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActiveAccount(user.getActiveAccount() == 1 ? 0 : 1);
            userRepo.save(user);
        }
    }

    @Override
    @Transactional
    public void activateUserAccount(String email, String token) {
        userRepo.findByActivationToken(token)
                .filter(user -> user.getEmail().equals(email))
                .ifPresent(user -> {
                    user.setActiveAccount(1);
                    user.setActivationToken("");
                    userRepo.save(user);
                });
    }

    @Override
    public boolean sendMailWithPasswordChange(String email) {
        User user = findByUserEmail(email);
        if (user != null){
            String token = tokenGenerator.generateToken();
            user.setActivationToken(token);
            mailSenderService.passwordChangeMail(email, token);
            userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean changeForgottenPassword(String email, String token, String newPassword, String repeatedPassword) {
        User user = findByUserEmail(email);
        if (user != null && user.getActivationToken().equals(token) && checkPasswordRepeat(newPassword, repeatedPassword)) {
            changePassword(newPassword, user.getId());
            user.setActivationToken("");
            userRepo.save(user);
            return true;
        }
        return false;
    }


    @Override
    public boolean checkTokenAndEmailMatch(String token, String email) {
        User user = userRepo.findByEmail(email);
        return user.getActivationToken().equals(token);
    }

    @Override
    public long countAdmins() {
        return userRepo.countByRoleAdmin();
    }


    @Override
    public void changePassword(String newPassword, Long userId) {
        userRepo.findById(userId).ifPresent(user -> {
            user.setPassword(
                    passwordEncoder.encode(newPassword));
            userRepo.save(user);
        });
    }
    private void sendConfirmationEmail(UserRegisterDto userRegisterDto, String token) {
        mailSenderService.confirmAccountCreationMail(userRegisterDto.getEmail(), token);
    }
    private boolean isAdmin(User user) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(ADMIN_ROLE));
    }

    private boolean checkCurrentPassword(String currentPassword, Long userId) {
        return userRepo.findById(userId)
                .map(user -> passwordEncoder.matches(currentPassword, user.getPassword()))
                .orElse(false);
    }

    private boolean checkPasswordRepeat(String newPassword, String confirmPassword) {
        return newPassword.equals(confirmPassword);
    }
}
