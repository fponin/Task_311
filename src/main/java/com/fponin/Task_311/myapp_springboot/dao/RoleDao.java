package com.fponin.Task_311.myapp_springboot.dao;

import com.fponin.Task_311.myapp_springboot.model.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleByName(String roleName);

    List<Role> getAllRole();
}
