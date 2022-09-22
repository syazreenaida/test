package ssf.miniproject.booklisttracker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonInclude;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLogin implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(UserLogin.class);
    private String username;
    private String id;
    private ArrayList<MoodBook> books = new ArrayList<>();

    public ArrayList<MoodBook> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<MoodBook> books) {
        this.books = books;
    }

    public UserLogin() {
        this.id = generateId(8);
    }

    public UserLogin(String username) {
        this.id = generateId(8);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String generateId(int numChars) 
    {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();

        while (sb.length() < numChars) 
        {
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numChars);
    }

    public void saveBook(String bookIdToSave, ArrayList<MoodBook> searchBooks) {
        MoodBook book = new MoodBook();

        for (MoodBook b : searchBooks) 
        {
            if (String.valueOf(b.getId()).equals(bookIdToSave)) 
            {
                book = b;
                for (MoodBook bk : books) {
                    if (bk.getId().equals(book.getId())) {
                        return;
                    }
                }
                books.add(book);
            }
        }
    }

    public void delBook(String bookIdToDel) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(bookIdToDel)) {
                books.remove(i);
            }
        }
    }
}
