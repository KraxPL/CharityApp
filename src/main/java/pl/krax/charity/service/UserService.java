package pl.krax.charity.service;

import pl.krax.charity.dto.UserDto;
import pl.krax.charity.dto.UserRegisterDto;
import pl.krax.charity.entities.User;

import java.util.List;
import java.util.Optional;


public interface UserService {
    void save(UserRegisterDto userRegisterDto);
    Optional<User> findById(Long userId);
    List<User> findAll();
    void update(UserDto userDto);
    User findByUserEmail(String email);
    UserDto findUserDtoByUserEmail(String email);
    boolean checkPasswordAndCurrentPassword(String newPassword, String confirmPassword, String currentPassword, Long userId);
    void changePassword(String newPassword, Long userId);
    List<UserDto> findAllAdmins();
    boolean delete(Long userId);
    long countAdmins();
    void createAdmin(UserDto userDto);
    UserDto findUserDtoById(Long id);
    void createUser(UserDto userDto);
    void createUserOrAdmin(UserDto userDto, boolean isAdmin);
    void lockOrUnlockUser(Long userId);
    void activateUserAccount(String email, String token);
    boolean sendMailWithPasswordChange(String email);
    boolean changeForgottenPassword(String email, String token, String newPassword, String repeatedPassword);
    boolean checkTokenAndEmailMatch(String token, String email);
}
