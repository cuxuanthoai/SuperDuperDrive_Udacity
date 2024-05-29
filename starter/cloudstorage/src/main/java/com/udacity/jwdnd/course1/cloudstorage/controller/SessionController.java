package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SessionController {

    public static final String LOGOUT_SUCCESS = "logoutSuccess";

    @GetMapping("/logout-success")
    public ModelAndView logoutView() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject(LOGOUT_SUCCESS, true);
        return modelAndView;
    }

}
