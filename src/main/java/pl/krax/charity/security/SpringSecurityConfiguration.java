package pl.krax.charity.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.krax.charity.service.impl.SpringDataUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return new SpringDataUserDetailsService();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(authorization ->
                        authorization
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/", "/register/**", "/activateUser", "/forgetPassword", "/password").permitAll()
                                .requestMatchers("/adminProfile", "/manage/**").hasRole("ADMIN")
                                .anyRequest()
                                .authenticated())
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .defaultSuccessUrl("/profile")
                                .usernameParameter("email")
                                .permitAll())
                .logout().logoutSuccessUrl("/")
                .and().authenticationProvider(authenticationProvider());
        return httpSecurity.build();
    }
}
