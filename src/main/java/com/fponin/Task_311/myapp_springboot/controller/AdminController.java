package com.fponin.Task_311.myapp_springboot.controller;


import com.fponin.Task_311.myapp_springboot.model.Role;
import com.fponin.Task_311.myapp_springboot.model.User;
import com.fponin.Task_311.myapp_springboot.service.RoleService;
import com.fponin.Task_311.myapp_springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    //Список всех @User
    @GetMapping()
    public String printUsers(Model model) {
        List<User> usersList = userService.getAllUsers();
        model.addAttribute("usersList", usersList);
        return "admin_panel";
    }

    //Удаление @User
    @PostMapping("/rem/{id}")
    public String deleteUsers(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    //Сохранить @User
    @GetMapping(value = "/addNewUser")
    public String addNewUsers(Model model) {
        User user = new User();
        user.addRoletoUser(roleService.getRoleByName("ROLE_USER"));
        model.addAttribute("roles", roleService.getAllRole());
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping(value = "/user/{id}")
    public String getUsers(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        model.addAttribute("roles", roleService.getAllRole());
        return "user";
    }

    @PostMapping("/{id}")
    public String saveUsers(@ModelAttribute("user") User user, @RequestParam("role") String[] role) {
        user.setRoles(addRole(role));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    private Set<Role> addRole(String[] role) {
        Set<Role> roleSet = new HashSet<>();
        for (String s : role) {
            roleSet.add(roleService.getRoleByName(s));
        }
        return roleSet;
    }

}