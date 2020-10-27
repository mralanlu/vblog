package com.northbund.vblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
public class ManagementController {

    @RequestMapping("/login")
    public String index(){
        return "login";
    }

}
