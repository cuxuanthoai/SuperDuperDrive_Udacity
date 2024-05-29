package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    public static final String ERRORS = "errors";
    public static final String SUCCESS = "success";
    private final UserService users;
    private final CredentialService credentials;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.users   = userService;
        this.credentials   = credentialService;
    }

public List<String> validate(Map<String, String> data) {
    var errors = new LinkedList<String>();

    if (data.get("url").isEmpty())
        errors.add("Please provide a valid URL");

    if (data.get("username").isEmpty())
        errors.add("Please enter your username");

    if (data.get("password").isEmpty())
        errors.add("Please provide your password.");

    return errors;
}

@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
public ModelAndView createView(
    HttpServletResponse response,
    Authentication authentication,
    @RequestParam Map<String, String> data
) {
    ModelAndView modelAndView = new ModelAndView("result");
    var UID = users.fetchUser(authentication.getName()).getUserId();

    credentials.saveCredential(
        new Credential(
            data.get("url"),
            data.get("username"),
            data.get("password"),
            UID
        )
    );

    var errors = validate(data);
    if (!errors.isEmpty()) {
        modelAndView.addObject(ERRORS, errors);
        modelAndView.addObject(SUCCESS, false);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } else {
        modelAndView.addObject(SUCCESS, true);
    }

    return modelAndView;
}



    @PutMapping(value = "{credentialId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView editView(
        HttpServletResponse response,
        Authentication authentication,
        @RequestParam Map<String, String> data
    ) {
        ModelAndView modelAndView = new ModelAndView("result");
        var UID = users.fetchUser(authentication.getName()).getUserId();

        credentials.saveCredential( new Credential(
                Integer.parseInt(data.get("credentialId")),
                data.get("url"),
                data.get("username"),
                data.get("password"),
                UID
            )
        );

        var errors = validate(data);
        if (!errors.isEmpty()) {
            modelAndView.addObject(ERRORS, errors);
            modelAndView.addObject(""
                + "", false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            modelAndView.addObject(SUCCESS, true);
        }

        return modelAndView;
    }


    @DeleteMapping(value = "{credentialId}")
    public ModelAndView removeView(
        HttpServletResponse response,
        @PathVariable Integer credentialId,
        Authentication authentication
    ) {
        ModelAndView modelAndView = new ModelAndView("result");
        List<String> errors = new ArrayList<>();

        try {
            var UID = users.fetchUser(authentication.getName()).getUserId();

            credentials.removeCredential((new Credential(credentialId, UID)));

            modelAndView.addObject(SUCCESS, true);
        } catch (Exception ignore) {
            errors.add("The credential removal failed due to a server problem.");
            modelAndView.addObject(ERRORS, errors);
            modelAndView.addObject(SUCCESS, false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        if (!errors.isEmpty()) {
            modelAndView.addObject(ERRORS, errors);
            modelAndView.addObject(SUCCESS, false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return modelAndView;
    }
}
