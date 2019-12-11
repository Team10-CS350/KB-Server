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
        u1.setEmail("a@kaist.ac.kr");
        u1.setPassword(encoder.encode("aaaaaaaaaa"));
        userRepository.save(u1);

        User u2 = new User();
        u2.setName("bbb");
        u2.setEmail("b@kaist.ac.kr");
        u2.setPassword(encoder.encode("bbbbbbbbbb"));
        userRepository.save(u2);

        User u3 = new User();
        u3.setName("ccc");
        u3.setEmail("c@kaist.ac.kr");
        u3.setPassword(encoder.encode("cccccccccc"));
        userRepository.save(u3);

        User u4 = new User();
        u4.setName("ddd");
        u4.setEmail("d@kaist.ac.kr");
        u4.setPassword(encoder.encode("dddddddddd"));
        userRepository.save(u4);

        User u5 = new User();
        u5.setName("eee");
        u5.setEmail("e@kaist.ac.kr");
        u5.setPassword(encoder.encode("eeeeeeeeee"));
        userRepository.save(u5);

        System.out.println("Created " + userRepository.count() + " dummy users");
    }
}
