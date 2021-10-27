package com.example.kurs.controller;

import com.example.kurs.dao.Role;
import com.example.kurs.dao.User;
import com.example.kurs.repo.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
public class MainController {

    private final UserRepo userRepo;
    public MainController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/registration")
    public String addNew(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, Model model) {
        User check = userRepo.findByUsername(user.getUsername());
        if (check == null) {
            user.setRoles(Collections.singleton(Role.USER));
            user.setActive(true);
            userRepo.save(user);
        }
        return "index";
    }

    @GetMapping("/user_home")
    public String user_home(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return "user_home";
    }

}
