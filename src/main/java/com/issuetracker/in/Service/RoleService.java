package com.issuetracker.in.Service;

import com.issuetracker.in.Repository.RoleRepository;
import com.issuetracker.in.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRep;

    public Role findByRoleName(String roleName){
        return roleRep.findByRoleName(roleName);
    }
}
