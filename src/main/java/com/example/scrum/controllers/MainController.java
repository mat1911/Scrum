package com.example.scrum.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    public MainController() {
        System.out.println("XD");
    }

    @GetMapping({"/", "/home"})
    public String home(){
        System.out.println("dupa");
        return "home";
    }


}
