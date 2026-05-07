package com.flexidle.flexidle_sb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//en kontroller för att sköta mappings osv
@Controller
public class HTMLController {

    @GetMapping("/")
    public String index()  {
        return "index";
    }

    @GetMapping("start")
    public String start()  {
        return "start";
    }
}
