package com.example.controllers;

import com.example.entities.User;
import com.example.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class UserController
{
    private UserService userService;

    @GetMapping("login")
    public String getRegistration(Model model)
    {
        model.addAttribute("userReg", new User());
        System.out.println("getRegistration из User");
        return "enter";
    }

    @PostMapping("reg")
    public String addNewUser(@ModelAttribute("userReg") User user)
    {
        userService.addUser(user);

        return "redirect:login";
    }
}
