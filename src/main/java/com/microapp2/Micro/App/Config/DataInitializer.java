package com.microapp2.Micro.App.Config;

import com.microapp2.Micro.App.Entities.Role;
import com.microapp2.Micro.App.Entities.User;
import com.microapp2.Micro.App.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        if (userRepository.findByEmail("admin@admin.com") == null) {
            User physicalUser = new User();
            physicalUser.setEmail("admin@admin.com");
            // Store password in plain text
            physicalUser.setPassword("1234");
            physicalUser.setRole(Role.PHYSICAL);
            userRepository.save(physicalUser);
        }
        
        if (userRepository.findByEmail("digital@digital.com") == null) {
            User digitalUser = new User();
            digitalUser.setEmail("digital@digital.com");
            // Store password in plain text
            digitalUser.setPassword("1234");
            digitalUser.setRole(Role.DIGITAL);
            userRepository.save(digitalUser);
        }
    }
}