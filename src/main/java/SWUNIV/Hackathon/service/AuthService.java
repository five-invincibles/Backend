package SWUNIV.Hackathon.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    public String encodePassword(String password) {
        String encoded = passwordEncoder.encode(password);

        return encoded;
    }
}
