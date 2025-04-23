package com.api.demo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.demo.model.User;
import com.api.demo.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ArrayList<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return this.userService.saveUser(user);
    }

    @GetMapping(path = "/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return this.userService.getUserById(id);
    }

    @PutMapping(path = "/{id}")
    public User updateById(@RequestBody User user, @PathVariable Long id) {
        return this.userService.updateById(user, id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable Long id) {
        boolean ok = this.userService.deleteById(id);
        if (ok) {
            return "Usuario con ID " + id + " fue eliminado correctamente.";
        } else {
            return "No se encontró el usuario con ID " + id;
        }
    }


    // Spring Data Envers
    @GetMapping("/{id}/revisions")
    public List<Number> getRevisions(@PathVariable Long id) {
    return userService.getRevisions(id);
    }

    @GetMapping("/{id}/revisions/{rev}")
    public User getRevision(@PathVariable Long id, @PathVariable Number rev) {
    return userService.getRevision(id, rev);
    }
    

}

