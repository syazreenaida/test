package ssf.miniproject.booklisttracker.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MoodBook implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(MoodBook.class);

    private String id;
    private String title;
    
    private String description;

    private ArrayList<String> authors;
    private String publishedDate;
    private String urlLink;
    private String imageUrl;
    private String previewLink;

    public MoodBook(){ }

    public MoodBook(String id, String title, String description,  ArrayList<String>authors, String publishedDate, String urlLink, String imageUrl, String previewLink){
        this.id = id;
        this.title = title;
        this.description = description;
        this.authors = authors;
        this.publishedDate = publishedDate;
        this.urlLink = urlLink;
        this.imageUrl = imageUrl;
        this.previewLink = previewLink;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }
    
    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors = authors;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }


    public static ArrayList<MoodBook> createJson(String json) throws IOException{
        ArrayList<MoodBook> bookList = new ArrayList<>();

        try(InputStream is = new ByteArrayInputStream(json.getBytes())){
            JsonReader reader = Json.createReader(is);
            JsonObject obj = reader.readObject();
            JsonArray itemsArr = obj.getJsonArray("items");

            for(int i = 0; i < itemsArr.size(); i++)
            {
                try{
                    JsonObject booksObject = itemsArr.getJsonObject(i);
                    JsonObject volumeObject = booksObject.getJsonObject("volumeInfo");
                    MoodBook book = new MoodBook();
                    book.setId(booksObject.getString("id"));
                    logger.info("book id >>> " + book.getId());
                    book.setTitle(volumeObject.getString("title"));
                    logger.info("book's title >>> " + book.getTitle());
                    book.setDescription(volumeObject.getString("description"));
                    logger.info("book's description >>> " + book.getDescription());
                    book.setPublishedDate(volumeObject.getString("publishedDate"));
                    logger.info("book's published date >>> " + book.getPublishedDate());
                    JsonObject imageLinks = volumeObject.getJsonObject("imageLinks");
                    book.setImageUrl(imageLinks.getString("thumbnail"));
                    logger.info("book's imageurl >>> " + book.getImageUrl());
                    book.setUrlLink(volumeObject.getString("infoLink"));
                    logger.info("book's url link >>> " + book.getUrlLink());
                    book.setPreviewLink(volumeObject.getString("previewLink"));
                    logger.info("book's preview link >>>> " + book.getPreviewLink());
                    JsonArray authorsArray = volumeObject.getJsonArray("authors");
                    ArrayList<String> authorsArrayList = new ArrayList<>();

                    if (authorsArray.size() != 0) 
                    {
                        for (int j = 0; j < authorsArray.size(); j++) 
                        {
                            authorsArrayList.add(authorsArray.getString(j));
                        }
                    }

                    book.setAuthors(authorsArrayList);
                    logger.info("book's authors >>>> " + book.getAuthors());
                    bookList.add(book);

                } catch (Exception e){
                    logger.info("Exception: {}", e.getMessage());
                }
            }
        }
         return bookList;
    }
}   

