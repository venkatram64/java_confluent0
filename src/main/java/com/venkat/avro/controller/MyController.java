package com.venkat.avro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("my-controller")
public class MyController {

    @RequestMapping("/hello")
    public String sayHello(){
        return "Hi Venkatram";
    }
}
