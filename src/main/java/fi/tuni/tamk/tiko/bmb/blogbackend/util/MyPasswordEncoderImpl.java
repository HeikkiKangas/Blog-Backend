package fi.tuni.tamk.tiko.bmb.blogbackend.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class MyPasswordEncoderImpl implements MyPasswordEncoder {
    PasswordEncoder passwordEncoder;

    public MyPasswordEncoderImpl() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public String encode(String input) {
        return passwordEncoder.encode(input);
    }
}
