package com.example.demo.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @RequestMapping("/workouts/all")
    public String sayHello() {
        return "HELLO SPRINGBOOT!";
    }

}

