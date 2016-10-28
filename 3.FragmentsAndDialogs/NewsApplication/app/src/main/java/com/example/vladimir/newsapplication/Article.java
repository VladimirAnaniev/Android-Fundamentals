package com.example.vladimir.newsapplication;

public class Article {

    private String Title;
    private String Details;
    private String Content;
    private Integer Image;

    public Article(String title, String details, String content, Integer image) {
        Title = title;
        Details = details;
        Content = content;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public String getDetails() {
        return Details;
    }

    public String getContent() {
        return Content;
    }

    public Integer getImage() {
        return Image;
    }
}
