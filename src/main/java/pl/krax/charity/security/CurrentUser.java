package pl.krax.charity.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import pl.krax.charity.dto.UserDto;

import java.util.Collection;

public class CurrentUser extends User {
    private final UserDto userDto;
    private final int activeAccount;
    public CurrentUser(String email, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       UserDto userDto, int activeAccount) {
        super(email, password, authorities);
        this.userDto = userDto;
        this.activeAccount = activeAccount;
    }
    public UserDto getUser() {return userDto;}
    public int getActiveAccount() {return activeAccount;}
}
