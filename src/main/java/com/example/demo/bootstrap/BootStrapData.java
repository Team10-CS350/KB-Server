package com.example.demo.bootstrap;

import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final UserRepository userRepository;

    public BootStrapData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Setting up dummy users...");

        User u1 = new User();
        u1.setName("aaa");
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
