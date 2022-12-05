package com.example.springauthen;

import com.example.springauthen.user.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringAuthenApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthenApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//		encoder = passwordEncoder();
//		String rawPassword = "12345";
//		String encodedPass = encoder.encode(rawPassword);
//		userRepository.save(new User("thomas",encodedPass));
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
