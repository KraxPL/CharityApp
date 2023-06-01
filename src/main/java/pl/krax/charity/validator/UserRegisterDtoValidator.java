package pl.krax.charity.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import pl.krax.charity.dto.UserRegisterDto;

@Component
public class UserRegisterDtoValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRegisterDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterDto userDto = (UserRegisterDto) target;

        if (!userDto.getPassword().equals(userDto.getRepeatedPassword())) {
            errors.rejectValue("repeatedPassword", "userRegisterDto.passwordMismatch", "Passwords do not match");
        }
    }
}

