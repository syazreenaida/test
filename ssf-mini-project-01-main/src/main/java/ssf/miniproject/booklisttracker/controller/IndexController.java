package ssf.miniproject.booklisttracker.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import ssf.miniproject.booklisttracker.model.UserLogin;
import ssf.miniproject.booklisttracker.repository.BooksRepo;
import ssf.miniproject.booklisttracker.service.BooksService;

@Controller
@RequestMapping(path = "/")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    UserLogin user;

    @Autowired
    BooksService service;

    @Autowired
    BooksRepo bookRepo;

    @GetMapping({"/", "/logout"})
    public String showIndexPage(Model model) {

        UserLogin user = new UserLogin("");
        model.addAttribute("user", user);

        return "login";
    }

    @GetMapping({"/survey", "/survey/{eUsername}"})
    public String getSavedBooks(@RequestParam(required = false, value = "username") String username,
                                @PathVariable(required = false) String eUsername,
                                Model model) {

        if (username == null) {
            username = eUsername;
        }

        user = bookRepo.getUser(username);
        model.addAttribute("user", user);

        return "survey";
    }

    @GetMapping({"/user", "/user/{eUsername}"})
    public String submitQuestion(@RequestParam(required = false, value = "username") String username, Model model){
        user = bookRepo.getUser(username);
        model.addAttribute("user", user);
        return "show";
    }
}