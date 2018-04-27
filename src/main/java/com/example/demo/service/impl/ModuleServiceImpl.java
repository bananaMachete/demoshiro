package com.example.demo.service.impl;

import com.example.demo.entity.Module;
import com.example.demo.persistence.ModuleMapper;
import com.example.demo.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleMapper moduleMapper;

    @Override
    public Set<Module> findAllModule(int rid) {
        return moduleMapper.getModuleByRoleId(rid);
    }
}
