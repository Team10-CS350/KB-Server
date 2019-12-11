package com.example.demo.services.User;

import com.example.demo.domain.User;
import com.example.demo.dto.User.UserRegistrationDTO;
import com.example.demo.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ModelMapper modelMapper = new ModelMapper();

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User registerUser(UserRegistrationDTO userRegistrationDTO) {
        User newUser = new User();
        modelMapper.map(userRegistrationDTO, newUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return saveUser(newUser);
    }


//    @Override
//    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
//        User loggedInUser = findByEmail(login)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + login));
//
//        List<String> roleArray = new ArrayList<String>();
//        roleArray.add("ADMIN");
//
//        GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
//
//        return new org.springframework.security.core.userdetails.User(loggedInUser.getEmail(), loggedInUser.getPassword(), Arrays.asList(authority));
//    }
}
