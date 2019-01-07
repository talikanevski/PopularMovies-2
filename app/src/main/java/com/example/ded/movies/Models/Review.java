package com.example.ded.movies.Models;

public class Review {
    private String author;
    String content;

    public Review() {}
    public String getAuthor (){return author;}
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getContent () {return content;}
    public void setContent(String content) {
        this.content = content;
    }
}
