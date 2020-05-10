package fi.tuni.tamk.tiko.bmb.blogbackend.util;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface MyPasswordEncoder {
    PasswordEncoder getPasswordEncoder();
    String encode(String input);
}
