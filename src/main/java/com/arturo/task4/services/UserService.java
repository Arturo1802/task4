package com.arturo.task4.services;


import com.arturo.task4.models.User;
import com.arturo.task4.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;


    public User checkAdmin(String mail, String pass) {
        return userRepo.checkAdmin(mail,pass);
    }

    public List<User> getAllUsers(){
        return userRepo.getAllUsers();
    }

    public int deleteUsers(List<User> users  ) {
        return userRepo.deleteUsers(users);
    }


    public int unblockUsers(List<User> users ) {
        return userRepo.unblockUsers(users );
    }
    public int registerUser(String name, String pass,String position, String mail) {
        return userRepo.register(name,pass,position,mail);
    }

    public int blockUsers(List<User> users ) {
        return userRepo.blockUsers(users  );
    }
}