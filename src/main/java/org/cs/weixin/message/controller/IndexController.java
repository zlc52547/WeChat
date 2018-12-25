package org.cs.weixin.message.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zlc on 2018/5/31.
 */
@Controller
@RequestMapping("/home")
public class IndexController {

    @RequestMapping("/index")
    public String index() {

        return "hello";
    }

    @RequestMapping("/doGet")
    public String doGet(){
        
        return  "";
    }
}