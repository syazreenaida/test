package ssf.miniproject.booklisttracker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ssf.miniproject.booklisttracker.model.MoodBook;

import java.util.ArrayList;

@Service
public class BooksService{

    @Value("searchParam")
    String searchParam;
    
    @Autowired(required=false)
    RedisTemplate<String, Object> template;

    private static final Logger logger = LoggerFactory.getLogger(BooksService.class);
    private static final String indexUrl = "https://www.googleapis.com/books/v1/volumes?";
    
    private ArrayList<MoodBook> books = new ArrayList<>();
    private String username;
    
    private static final String bookAPIKey = System.getenv("BOOK_API_KEY");

    
    
    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public ArrayList<MoodBook> getBooks(){
        String bookUrl = UriComponentsBuilder.fromUriString(indexUrl)
                        .query("q=subject:fiction&orderBy=newest")
                        .toUriString();

        logger.info("complete books uri for the index page " + bookUrl);

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        ArrayList<MoodBook> bookList = new ArrayList<>();
        try {
            resp = template.getForEntity(bookUrl, String.class);
            bookList = MoodBook.createJson(resp.getBody());
            
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return bookList;
    }

    public ArrayList<MoodBook> getSearchBooks(String keywords){
        this.searchParam = keywords;
        
        String bookUrl = UriComponentsBuilder.fromUriString(indexUrl)
                        .queryParam("q", keywords)
                        .toUriString();

        logger.info("complete books uri for the search page " + bookUrl);

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        ArrayList<MoodBook> bookList = new ArrayList<>();
        try {
            resp = template.getForEntity(bookUrl, String.class);
            bookList = MoodBook.createJson(resp.getBody());
            
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return bookList;
    }

}