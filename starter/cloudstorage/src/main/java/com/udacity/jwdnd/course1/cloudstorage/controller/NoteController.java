package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.util.ArrayList;
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
@RequestMapping("/notes")
public class NoteController {

    public static final String SUCCESS = "success";
    public static final String ERRORS = "errors";
    private final UserService users;
    private final NoteService notes;

    public NoteController(UserService userService, NoteService noteService) {
        this.users = userService;
        this.notes = noteService;
    }

    public List<String> validate(Map<String, String> data) {
        List<String> errors = new ArrayList<String>();

        if (data.get("noteTitle").isEmpty())
            errors.add("Note title must not be empty.");

        if (data.get("noteDescription").isEmpty())
            errors.add("Note description must not be empty.");

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


    notes.save(
        new Note(
            null,
            data.get("noteTitle"),
            data.get("noteDescription"),
            UID
        )
    );

    var errors = validate(data);
    modelAndView.addObject(SUCCESS, true);

    if (!errors.isEmpty()) {
        modelAndView.addObject(ERRORS, errors);
        modelAndView.addObject(SUCCESS, false);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    return modelAndView;
}

    @DeleteMapping(value = "/{noteId}")
    public ModelAndView removeView(
        HttpServletResponse response,
        @PathVariable Integer noteId,
        Authentication authentication
    ) {
        ModelAndView modelAndView = new ModelAndView("result");
        List<String> errors = new ArrayList<>();

        try {
            var UID = users.fetchUser(authentication.getName()).getUserId();
            notes.remove(new Note(noteId, UID));

            modelAndView.addObject(SUCCESS, true);
        } catch (Exception ignore) {
            errors.add("The note removal failed due to a server error");
            modelAndView.addObject(ERRORS, errors);
            modelAndView.addObject(ERRORS, false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        if (!errors.isEmpty()) {
            modelAndView.addObject(ERRORS, errors);
            modelAndView.addObject(SUCCESS, false);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } else {
            modelAndView.addObject(SUCCESS, true);
        }

        return modelAndView;
    }

    @PutMapping(value = "/{noteId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView editView(
        HttpServletResponse response,
        Authentication authentication,
        @RequestParam Map<String, String> data
    ) {
        ModelAndView modelAndView = new ModelAndView("result");
        var UID = users.fetchUser(authentication.getName()).getUserId();

        notes.save(
            new Note(
                Integer.parseInt(data.get("noteId")),
                data.get("noteTitle"),
                data.get("noteDescription"),
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

}
