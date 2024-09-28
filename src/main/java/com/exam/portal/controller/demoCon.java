package com.exam.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class demoCon {

    @GetMapping("/1")
    public String demo() {
        return "Finally";
    }
}
