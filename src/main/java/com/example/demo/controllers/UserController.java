package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.modules.ApiResponse;
import org.springframework.web.bind.annotation.*;
import com.example.demo.sevices.UserService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/userapi")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @PostMapping("/signup")
    public String signup(@RequestBody User user){
        return service.signup(user);
    }
    @GetMapping("/test")
    public String test() {
        return "Working";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return service.findAll();
    }
    @PostMapping("/login")
    public ApiResponse login(@RequestBody User user){
        return service.login(user);
    }

}
