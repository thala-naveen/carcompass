package com.practice.carcompass.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResource {

    @GetMapping(value = "/test")
    public String test(){
        return "Hello cars";
    }
}
