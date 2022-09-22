package ssf.miniproject.booklisttracker.controller;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssf.miniproject.booklisttracker.model.MoodBook;
import ssf.miniproject.booklisttracker.model.UserLogin;
import ssf.miniproject.booklisttracker.repository.BooksRepo;
import ssf.miniproject.booklisttracker.service.BooksService;

@Controller
@RequestMapping("/book")
public class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Value("searchParam")
    String searchParam;
    
    @Autowired
    UserLogin currentUser;

    @Autowired
    BooksRepo bookRepo;

    @Autowired
    BooksService service;

    @GetMapping("/search")
    public String searchBooks(@RequestParam("searchParam") String searchKeyword,
                                 @RequestParam String username,
                                 Model model) {

        logger.info("search terms >>> " + searchKeyword);
        
        currentUser = bookRepo.getUser(username);
        model.addAttribute("user", currentUser);

        ArrayList<MoodBook> bookList = service.getSearchBooks(searchKeyword);
        bookRepo.setSearchedBooks(bookList);
        
        searchParam = searchKeyword;
        
        model.addAttribute("searchParam", searchParam);
        model.addAttribute("searchResults", bookList);

        logger.info("user bookids: {}", currentUser.getBooks().stream().map(MoodBook::getId).collect(Collectors.toList()));

        return "searchResult";

    }
    
    @PostMapping(path="/save/{toBeSavedId}", consumes="application/x-www-form-urlencoded", produces="text/html")
    public String saveBook(@PathVariable String toBeSavedId, 
                            @RequestParam("username") String username,
                            Model model) {

        if(!currentUser.getBooks().contains(toBeSavedId)){
            currentUser.saveBook(toBeSavedId, bookRepo.getSearchedBooks());
            bookRepo.save(currentUser);
        }

        model.addAttribute("user", currentUser);
        model.addAttribute("toBeSavedId", toBeSavedId);
        model.addAttribute("searchParam", searchParam);
        model.addAttribute("searchResults", bookRepo.getSearchedBooks());

        logger.info("id to save: {}", toBeSavedId);
        logger.info("user books: {}", currentUser.getBooks().stream().map(MoodBook::getId).collect(Collectors.toList()));
        return "searchResult";
    }

    @PostMapping(path="/delete/{toBeDeletedId}", consumes="application/x-www-form-urlencoded", produces="text/html")
    public String deleteBook(@PathVariable String toBeDeletedId,
                           @RequestParam("username") String username,
                           Model model) {

        currentUser.delBook(toBeDeletedId);
        bookRepo.save(currentUser);

        model.addAttribute("user", currentUser);
        model.addAttribute("toDeleteId", toBeDeletedId);
        model.addAttribute("searchParam", searchParam);
        model.addAttribute("searchResults", bookRepo.getSearchedBooks());

        logger.info("id to delete: {}", toBeDeletedId);
        logger.info("user books: {}", currentUser.getBooks().stream().map(MoodBook::getId).collect(Collectors.toList()));
        return "searchResult";
    }

    @PostMapping(path="/deleteFromHome/{toBeDeletedId}", consumes="application/x-www-form-urlencoded", produces="text/html")
    public String deleteBookFromHome(@PathVariable String toBeDeletedId,
                             @RequestParam("username") String username,
                             Model model) {
        currentUser.delBook(toBeDeletedId);
        bookRepo.save(currentUser);

        model.addAttribute("user", currentUser);
        model.addAttribute("toDeleteId", toBeDeletedId);
        model.addAttribute("searchParam", searchParam);
        model.addAttribute("searchResults", bookRepo.getSearchedBooks());

        logger.info("id to delete: {}", toBeDeletedId);
        logger.info("user books: {}", currentUser.getBooks().stream().map(MoodBook::getId).collect(Collectors.toList()));
        return "show";
    }

    // @GetMapping(path = "/search")
    // public String getBooks(Model model){
    //     ArrayList<Book> bookList = service.getSearchBooks(keywords);

    //     model.addAttribute("bookList", bookList);
    //     return "searchResult";
    // }

   
}
