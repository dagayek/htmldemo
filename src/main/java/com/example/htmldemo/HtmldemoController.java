package com.example.htmldemo;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HtmldemoController {

    @RequestMapping("/greeting")
    public String index(@RequestParam(name="name", required=false, defaultValue="World") String name) {
        return "Hello " + name;
    }

}