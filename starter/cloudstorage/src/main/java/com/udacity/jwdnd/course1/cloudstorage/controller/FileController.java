package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/files")
public class FileController {

    public static final String ERRORS = "errors";
    public static final String SUCCESS = "success";
    private final UserService users;
    private final FileService files;

    public FileController(UserService userService, FileService fileService) {
        this.users = userService;
        this.files = fileService;
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ModelAndView uploadView(
        HttpServletResponse response,
        Authentication authentication,
        @RequestParam(name = "file") MultipartFile multipartFile
    ) {
        ModelAndView modelAndView = new ModelAndView("result");
        var errors = new LinkedList<>();

        if (multipartFile.isEmpty()) {
            errors.add("File field cannot be left empty");
        } else {
            var UID = users.fetchUser(authentication.getName()).getUserId();
            try {
                var file = new File(
                    multipartFile.getOriginalFilename(),
                    multipartFile.getSize(),
                    multipartFile.getContentType(),
                    multipartFile.getBytes(),
                    UID
                );

                if (files.isPresent(file)) {
                    errors.add("The file has already been stored");
                } else {
                    files.save(file);
                }

            } catch (Exception e) {
                errors.add("An error occurred");
            }
        }

        if (!errors.isEmpty()) {
            modelAndView.addObject(ERRORS, errors);
            modelAndView.addObject(SUCCESS, false);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            modelAndView.addObject(SUCCESS, true);
        }

        return modelAndView;
    }

@GetMapping(value = "/{fileId}")
public ModelAndView downloadView(
    HttpServletResponse response,
    Authentication authentication,
    @PathVariable Integer fileId
) {
    ModelAndView modelAndView = new ModelAndView();
    var UID = users.fetchUser(authentication.getName()).getUserId();
     var file = files.fetch(new File(fileId, UID));

    if (file != null) {
        return new ModelAndView(String.valueOf(new ResponseEntity<>(
            new ByteArrayResource(file.getData()),
            HttpStatus.OK
        )));
    } else {
        modelAndView.setViewName("error");
        modelAndView.addObject("message", "The file could not be located.");
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return modelAndView;
    }
}

    @DeleteMapping(value = "/{fileId}")
    public ModelAndView removeView(
        Authentication authentication,
        HttpServletResponse response,
        @PathVariable Integer fileId
    ) {
        ModelAndView modelAndView = new ModelAndView("result");
        var UID = users.fetchUser(authentication.getName()).getUserId();
        List<String> errors = new ArrayList<>();

        try {
            files.delete(new File(fileId, UID));
            modelAndView.addObject(SUCCESS, true);
        } catch (Exception ignore) {
            errors.add("The file removal failed due to a server error");
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
