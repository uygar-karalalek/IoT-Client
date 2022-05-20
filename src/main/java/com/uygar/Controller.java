package com.uygar;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ciao")
public class Controller {

    @GetMapping()
    public void method() {

    }

}

