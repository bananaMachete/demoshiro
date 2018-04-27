package com.example.demo.service;

import com.example.demo.entity.Module;
import com.example.demo.entity.Role;

import java.util.Set;

public interface ModuleService {
   
    public Set<Module> findAllModule(int uid);
}
