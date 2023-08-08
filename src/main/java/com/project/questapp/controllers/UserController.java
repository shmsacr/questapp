package com.project.questapp.controllers;

import com.project.questapp.entities.User;
import com.project.questapp.repos.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    }
    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId){
        //custom exception
        return userRepository.findById(userId).orElse(null);
    }
    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId,@RequestBody User newUser){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            User founderUser = user.get();
            founderUser.setUserName(newUser.getUserName());
            founderUser.setPassword(newUser.getPassword());
            userRepository.save(founderUser);
            return founderUser;
        }else
            return null;
    }
    @DeleteMapping("/{userId}")
    public void deletOneUser(@PathVariable Long userId){
        userRepository.deleteById(userId);
    }
}
