package com.example.kurs.controller;

import com.example.kurs.dao.Role;
import com.example.kurs.dao.User;
import com.example.kurs.repo.UserRepo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Controller
@RequestMapping("/adm")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserRepo userRepo;


    public AdminController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String admin_home(Model model) {
        return "admin_home";
    }

    @GetMapping("/all_user")
    public String users(Model model) {
        model.addAttribute("users", userRepo.findAll());
        model.addAttribute("user", new User());
        return "all_user";
    }

    @GetMapping("/new_user")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("role", Role.values());
        return "new_user";
    }

    @PostMapping("/new_user")
    public String newUser(@ModelAttribute User user, Model model) throws ParseException {
        User check = userRepo.findByUsername(user.getUsername());
        if (check == null) {
            user.setActive(true);
            userRepo.save(user);
            return "redirect:/adm/all_user";
        }
        return "new_user";
    }

    @RequestMapping(value = "/delete/{Id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer Id, Model model) {
        userRepo.delete(userRepo.findById(Id).isPresent() ? userRepo.findById(Id).get() : new User());
        return "redirect:/adm/all_user";
    }

    @RequestMapping(value = "/ban/{Id}", method = RequestMethod.GET)
    public String ban(@PathVariable Integer Id, Model model) {
        User user1=userRepo.findById(Id).isPresent() ? userRepo.findById(Id).get() : new User();
        user1.setActive(false);
        userRepo.save(user1);
        return "redirect:/adm/all_user";
    }

    @RequestMapping(value = "/active/{Id}", method = RequestMethod.GET)
    public String active(@PathVariable Integer Id, Model model) {
        User user1=userRepo.findById(Id).isPresent() ? userRepo.findById(Id).get() : new User();
        user1.setActive(true);
        userRepo.save(user1);
        return "redirect:/adm/all_user";
    }
}
