package com.app.service.implementation;

import com.app.model.Role;
import com.app.repository.RoleRepository;
import com.app.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role findRoleByName(String name) {
        return this.roleRepository.getRoleByName(name);
    }
}
