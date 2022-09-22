package ssf.miniproject.booklisttracker.repository;

import java.util.ArrayList;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ssf.miniproject.booklisttracker.model.MoodBook;
import ssf.miniproject.booklisttracker.model.UserLogin;

@Repository
public class BooksRepo {
    private static final Logger logger = LoggerFactory.getLogger(BooksRepo.class);

    private UserLogin user;
    private String username;
    private ArrayList<MoodBook> savedBooks = new ArrayList<>();
    private ArrayList<MoodBook> searchedBooks = new ArrayList<>();

    @Autowired
    RedisTemplate<String, Object> template;

    public BooksRepo() {

    }

    public BooksRepo(String username) {
        this.username = username;
    }

    public void save(UserLogin user) {
        template.opsForValue().set(user.getUsername(), user);
    }

    public UserLogin getUser(String username) {
        logger.info("username used >>> " + username);
        UserLogin user = new UserLogin(username);
        Set<String> keys = template.keys("*");
        if (keys.contains(username)) {
            user = (UserLogin) template.opsForValue().get(username);
        }
        return user;
    }

    public ArrayList<MoodBook> getSavedBooks() {
        return savedBooks;
    }

    public void setSavedBooks(ArrayList<MoodBook> savedBooks) {
        this.savedBooks = savedBooks;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserLogin getUser() {
        return user;
    }

    public void setUser(UserLogin user) {
        this.user = user;
    }

    public ArrayList<MoodBook> getSearchedBooks() {
        return searchedBooks;
    }

    public void setSearchedBooks(ArrayList<MoodBook> searchedBooks) {
        this.searchedBooks = searchedBooks;
    }
}

