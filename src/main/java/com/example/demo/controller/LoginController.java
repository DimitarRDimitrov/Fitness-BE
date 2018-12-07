package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginController {

    @Autowired
    UserRepository userRepository;

    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }

    public boolean isValidLogin(String userName, String password) {
        User user = getUserByUserName(userName);
        return user != null && password.equals(user.getPassword());
    }

    public boolean registerUser(String username, String password, String email, String fName, String lName, String phone) {
        User user = new User(username, fName, lName, email, password, phone);
        userRepository.save(user);
        return true;
    }
}
