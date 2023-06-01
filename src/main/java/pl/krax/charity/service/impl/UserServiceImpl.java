package pl.krax.charity.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.krax.charity.dto.UserDto;
import pl.krax.charity.dto.UserRegisterDto;
import pl.krax.charity.entities.User;
import pl.krax.charity.mapper.UserMapper;
import pl.krax.charity.repo.RoleRepository;
import pl.krax.charity.repo.UserRepository;
import pl.krax.charity.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public void save(UserRegisterDto userRegisterDto) {
        userRepo.save(userMapper.toUser(userRegisterDto, roleRepository, passwordEncoder));
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
    public boolean checkPasswordAndCurrentPassword(String newPassword, String confirmPassword, String currentPassword, Long userId) {
        return checkPasswordRepeat(newPassword, confirmPassword) &&
                checkCurrentPassword(currentPassword, userId);
    }

    @Override
    public void changePassword(String newPassword, Long userId) {
        userRepo.findById(userId).ifPresent(user -> {
            user.setPassword(
                    passwordEncoder.encode(newPassword));
            userRepo.save(user);
        });
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
