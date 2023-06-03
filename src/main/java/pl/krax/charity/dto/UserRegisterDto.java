package pl.krax.charity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 8)
    @Pattern.List({
            @Pattern(regexp = ".*\\p{Lower}.*", message = "Password must contain at least one lowercase letter."),
            @Pattern(regexp = ".*\\p{Upper}.*", message = "Password must contain at least one uppercase letter."),
            @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit."),
            @Pattern(regexp = ".*[@#$%^&+=].*", message = "Password must contain at least one special character.")
    })
    private String password;
    @NotBlank
    @Size(min = 8)
    private String repeatedPassword;
}
