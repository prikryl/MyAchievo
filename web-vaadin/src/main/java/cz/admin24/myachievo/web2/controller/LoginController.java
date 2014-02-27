package cz.admin24.myachievo.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login/")
public class LoginController {

//    @RequestMapping("/")
//    public @ResponseBody String index() {
//        return "login";
//    }
    @RequestMapping()
    public String index() {
        return "login";
    }
}
