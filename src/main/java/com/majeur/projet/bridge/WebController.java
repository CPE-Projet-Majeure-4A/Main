package com.majeur.projet.bridge;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {


    @RequestMapping(value = { "/", "/carte" }, method = RequestMethod.GET)
    public String index(Model model) {
        return "carte";
    }


}
