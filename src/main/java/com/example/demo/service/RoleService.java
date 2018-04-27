package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Set;

public interface RoleService {

    public Set<Role> getRolesByUserId(int uid);
}
