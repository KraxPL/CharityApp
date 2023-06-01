package pl.krax.charity.service.impl;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.krax.charity.dto.UserDto;
import pl.krax.charity.entities.User;
import pl.krax.charity.mapper.UserMapper;
import pl.krax.charity.security.CurrentUser;
import pl.krax.charity.service.UserService;

import java.util.HashSet;
import java.util.Set;
@Service
@Transactional
public class SpringDataUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findByUserEmail(email);
        UserDto userDto = UserMapper.INSTANCE.toDto(user);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(role ->
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
        return new CurrentUser(
                user.getEmail(), user.getPassword(),
                grantedAuthorities, userDto, user.getActiveAccount());
    }
}
