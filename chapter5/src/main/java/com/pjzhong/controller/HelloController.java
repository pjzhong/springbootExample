package com.pjzhong.controller;

import com.pjzhong.exception.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() throws Exception {
        throw new Exception("Exception");
    }

    @RequestMapping("/json")
    public String json(ModelMap map) throws MyException {
        throw new MyException("Error");
    }

    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("host", "http://www.pjzhong.com");
        return "index";
    }

}
