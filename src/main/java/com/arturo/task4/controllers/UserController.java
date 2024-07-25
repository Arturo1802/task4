package com.arturo.task4.controllers;

import com.arturo.task4.models.User;
import com.arturo.task4.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@RequestMapping("/")
@Controller
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String entry(Model model){
        model.addAttribute("user", new User());
        return "login";
    }
    @GetMapping("login")
    public String login(){
        return "login";
    }

    @GetMapping("/error")
    public String error(){
        return "404";
    }
    @PostMapping("api/v1/user/block")
    public ResponseEntity<String>  blockUsers(@RequestBody List<User> users ){
        try {
            int updatedRows = userService.blockUsers(users);
            return new ResponseEntity<>("Users blocked successfully: " + updatedRows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error blocking users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("api/v1/user/unblock")
    public ResponseEntity<String> unblockUser(@RequestBody List<User> users ){
        try {
            int updatedRows = userService.unblockUsers(users);
            return new ResponseEntity<>("Users unblocked successfully: " + updatedRows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error unblocking users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("api/v1/user/register")
    public ResponseEntity<String> register(@RequestParam("mail") String mail,@RequestParam("password") String pass,@RequestParam("position") String position,@RequestParam("name") String name){
        try {
            int updatedRows = userService.registerUser(name,pass,position,mail);
            return new ResponseEntity<>("User registered successfully:" + updatedRows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error unblocking users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("api/v1/user/delete")
    public ResponseEntity<String> deleteUser(@RequestBody List<User> users ){
        try {
            int updatedRows = userService.deleteUsers(users);
            return new ResponseEntity<>("Users unblocked successfully: " + updatedRows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error unblocking users", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("api/v1/user")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("dashboard")
    public String accessDashboard(@RequestBody MultiValueMap<String, String> formData, Model model )
    {
        var mail=formData.get("mail").get(0);
        var pass=formData.get("pass").get(0);
        User user=userService.checkAdmin(mail,pass);
        try {
            model.addAttribute("users",userService.getAllUsers());
            model.addAttribute("adminStatus",user.getAdminStatus());
            model.addAttribute("username",user.getName());

            return  (pass.equals(user.getPass()) ? "dashboard" : "loginfail");
        }catch(EmptyResultDataAccessException e){
            model.addAttribute("error","Error User Is Blocked or does not exist");

            return "login";
        }
    }
}
