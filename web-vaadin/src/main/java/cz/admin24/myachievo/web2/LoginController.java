package cz.admin24.myachievo.web2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login.do")
    public String index() {
        return "login";
    }
}
