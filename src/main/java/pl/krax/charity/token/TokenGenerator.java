package pl.krax.charity.token;

import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class TokenGenerator {
    public String generateToken() {
        UUID token = UUID.randomUUID();
        return token.toString();
    }
}