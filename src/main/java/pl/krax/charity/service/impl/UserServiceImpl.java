package pl.krax.charity.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
    public void update(User user) {
        userRepo.save(user);
    }

    @Override
    @Transactional
    public User findByUserEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
