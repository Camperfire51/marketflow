package com.camperfire.marketflow.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @CrossOrigin(origins = "http://localhost:3000") // Allow requests from this origin
    @GetMapping("/example")
    public String exampleEndpoint() {
        return "CORS enabled!";
    }
}
