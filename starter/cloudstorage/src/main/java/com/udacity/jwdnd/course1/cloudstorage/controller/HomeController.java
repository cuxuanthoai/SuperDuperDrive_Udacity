package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomeController {

    public static final String NOTES = "notes";
    public static final String FILES = "files";
    public static final String CREDENTIALS = "credentials";
    private final UserService users;
    private final NoteService notes;
    private final FileService files;
    private final CredentialService credentials;

    public HomeController(
        UserService users,
        NoteService notes,
        FileService files,
        CredentialService credentials
    ) {
        this.users = users;
        this.notes = notes;
        this.files = files;
        this.credentials = credentials;
    }

    @GetMapping()
    public ModelAndView getHomeView(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("home");

        try {
            var UID = users.fetchUser(authentication.getName()).getUserId().toString();
            modelAndView.addObject(NOTES, notes.fetchAllByUserId(UID));
            modelAndView.addObject(FILES, files.fetchAllByUserId(UID));
            modelAndView.addObject(CREDENTIALS, credentials.fetchAllByUserId(UID));
        } catch (Exception ignored) {
            modelAndView.setViewName("redirect:/logout-success");
        }

        return modelAndView;
    }
}
