package com.example.demo.bootstrap;

import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder encoder;

    public BootStrapData(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Setting up dummy users...");

        User u1 = new User();
        u1.setName("a");
        u1.setEmail("a@a.a");
        u1.setPassword(encoder.encode("aaa"));
        userRepository.save(u1);

        User u2 = new User();
        u2.setName("bbb");
        userRepository.save(u2);

        User u3 = new User();
        u3.setName("ccc");
        userRepository.save(u3);

        User u4 = new User();
        u4.setName("ddd");
        userRepository.save(u4);

        User u5 = new User();
        u5.setName("eee");
        userRepository.save(u5);

        System.out.println("Created " + userRepository.count() + " dummy users");
    }
}
