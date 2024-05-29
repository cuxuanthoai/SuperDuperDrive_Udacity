package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    public static final String SIGNUP_ERROR = "signupError";
    public static final String SUCCESS = "signupSuccess";
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public ModelAndView signupView() {
        return new ModelAndView("/signup");
    }

    @PostMapping()
    public ModelAndView signupUser(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView();
        String signupError = null;

        if (!userService.isUsernameFree(user.getUsername())) {
            signupError = "The username '" + user.getUsername() + "' already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);

            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again.";
            }
        }

        if (signupError != null) {
            modelAndView.addObject(SIGNUP_ERROR, signupError);
            modelAndView.setViewName("/signup");
        } else {
            modelAndView.addObject(SUCCESS, true);
            modelAndView.setViewName("/login");
        }

        return modelAndView;
    }
}
