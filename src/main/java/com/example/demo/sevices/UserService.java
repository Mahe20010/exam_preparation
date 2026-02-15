package com.example.demo.sevices;

import com.example.demo.entity.User;
import com.example.demo.modules.ApiResponse;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    public String signup(User user){

        if(repository.findByNumber(user.getNumber())!=null){
            return "user already exists";
        }
        repository.save(user);
        return "Signup SuccessFul";


    }

    public ApiResponse login(User user){
        User existingUser=repository.findByNumber(user.getNumber());
        if(existingUser==null){
            return new ApiResponse("error","user not found",null);
        }
        if(!existingUser.getPassword().equals(user.getPassword())){
            return new ApiResponse("error","invalid password",null);
        }
        existingUser.setPassword(null);
        return new ApiResponse("success","Login Successfull",existingUser);
       // if(repository.findByNumber(user.getNumber()))
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
