package com.hashicorp.test.controllers;

import com.hashicorp.test.DAO.User;
import com.hashicorp.test.DAO.UserTable;
import com.hashicorp.test.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRESTController {
    @Autowired
    UsersService usersService;

    @GetMapping(value = "/api/users/{id}")
    User getUserById(@PathVariable("id") String id) {
        return usersService.getUserById(id);
    }

    @GetMapping(value = "/api/users/")
    List<UserTable> getUsers() {
        return usersService.getUsers();
    }
}
