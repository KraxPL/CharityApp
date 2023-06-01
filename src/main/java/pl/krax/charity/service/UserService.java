package pl.krax.charity.service;

import pl.krax.charity.dto.UserRegisterDto;
import pl.krax.charity.entities.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    void save(UserRegisterDto userRegisterDto);
    Optional<User> findById(Long userId);
    List<User> findAll();
    void update(User user);
    User findByUserEmail(String email);
}
