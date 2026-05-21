package com.flexidle.flexidle_sb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

//en kontroller för att sköta mappings osv
@Controller
public class HTMLController {

    @GetMapping("/")
    public String index()  {
        return "index";
    }

    @GetMapping("settings")
    public String settings() {
        return "settings";
    }

    @GetMapping("Flexidle")
    public String flexidle() {
        return "flexidle";
    }

    @GetMapping("statistics")
    public String statistics() {
        return "statistics";
    }

    @GetMapping("rules")
    public String rules() {
        return "rules";
    }

    @GetMapping("result-pop-up-won")
    public String resultPopUpWon() {
        return "result-pop-up-won";
    }

    @GetMapping("result-pop-up-lost")
    public String resultPopUpLost() {
        return "result-pop-up-lost";
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }

    @GetMapping("/flexidle")
    public String game(@RequestParam(required = false) String bg,
                       @RequestParam(required = false) String shape,
                       @RequestParam(required = false) String language,
                       @RequestParam(required = false) Integer length,
                       @RequestParam(required = false) Integer guesses,
                       Model model) {
        model.addAttribute("bg", bg);
        model.addAttribute("shape", shape);
        model.addAttribute("language", language);
        model.addAttribute("length", length);
        model.addAttribute("guesses", guesses);
        return "flexidle";
    }
}
