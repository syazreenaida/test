package ssf.miniproject.booklisttracker.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;

@Component
public class Genre implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Genre.class);

    private String name;
    private ArrayList<MoodBook> bookArr = new ArrayList<>();

    private static final long serialVersionUID = 1L;

    public Genre () {

    }
    
    public Genre(String name) {
        this.name = name;
    }

    public static Logger getLogger() {
        return logger;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MoodBook> getBookArr() {
        return bookArr;
    }

    public void setArr(ArrayList<MoodBook> bookArr) {
        this.bookArr = bookArr;
    }

    public void addJournal(MoodBook book) {
        bookArr.add(book);
    }
}
