package pl.sda.javapoz.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by pablo on 24.03.17.
 */
public class PasswordEncoder {

    /**
     * simple method to encoding users password
     * @param password to encode
     * @return encoded password
     */
    public static String encode(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        return password;
    }
}
