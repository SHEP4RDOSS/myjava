package com.example.kurs.controller;

import com.example.kurs.repo.UserRepo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Rest_Controller {
    private final UserRepo userRepo;

    public Rest_Controller(UserRepo userRepo) {
        this.userRepo = userRepo;
    }
}
