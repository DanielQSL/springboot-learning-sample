package com.qsl.springboot.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author DanielQSL
 */
@RestController
public class HelloController {

    @PreAuthorize("hasAuthority('test')")
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
