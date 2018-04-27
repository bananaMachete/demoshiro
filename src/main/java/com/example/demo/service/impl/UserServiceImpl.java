package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.persistence.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserByUserName(String username) {
        System.out.println("-------------------UserServiceImpl excutoer---------------------");
        return userMapper.getUserByUserName(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userMapper.findAllUsers();
    }
}
