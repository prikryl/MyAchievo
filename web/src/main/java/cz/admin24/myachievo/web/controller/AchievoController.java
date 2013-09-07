package cz.admin24.myachievo.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.admin24.myachievo.web.dao.AchievoAccountDAO;
import cz.admin24.myachievo.web.entity.AchievoAccount;
import cz.admin24.myachievo.web.form.AchievoCredentials;
import cz.admin24.myachievo.web.security.SocialUserWithId;

@Controller
@RequestMapping("/achievo")
public class AchievoController {
    @Autowired
    private AchievoAccountDAO achievoAccountDAO;


    @RequestMapping(value = "updateCredentials", method = RequestMethod.GET)
    public String updateCredentials(Model model) {
        AchievoCredentials credentials = new AchievoCredentials();
        SocialUserWithId actualUser = (SocialUserWithId) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AchievoAccount account = achievoAccountDAO.findById(actualUser.getUserUUId());
        credentials.setUsername(account.getUsername());
        model.addAttribute("credentials", credentials);
        return "/achievo/updateCredentials";
    }


    @RequestMapping(value = "updateCredentials", method = RequestMethod.POST)
    public String updateCredentials(@ModelAttribute("credentials") @Valid AchievoCredentials credentials, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "/achievo/updateCredentials";
        }

        SocialUserWithId actualUser = (SocialUserWithId)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        achievoAccountDAO.upsertCredentials(actualUser.getUserUUId(), credentials.getUsername(),
                credentials.getPassword());
        model.addAttribute("message", "Successfully saved credentials: " + credentials.getUsername());
        return "/achievo/updateCredentials";
    }

}
