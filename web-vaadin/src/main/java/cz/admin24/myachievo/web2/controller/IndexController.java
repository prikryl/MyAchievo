package cz.admin24.myachievo.web2.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping("")
    public void index(HttpServletResponse reponse) throws IOException {
        reponse.sendRedirect("ui");
    }
}
