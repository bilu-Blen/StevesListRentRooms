package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void saveUser(User user){
        Role role = roleRepository.findByRole("USER");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    public void saveAdmin(User user){
        Role role = roleRepository.findByRole("ADMIN");
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

}
