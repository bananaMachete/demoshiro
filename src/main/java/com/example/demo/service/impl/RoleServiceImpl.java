package com.example.demo.service.impl;

import com.example.demo.entity.Role;
import com.example.demo.persistence.RoleMapper;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public Set<Role> getRolesByUserId(int uid) {
        return roleMapper.getRolesByUserId(uid);
    }
}
