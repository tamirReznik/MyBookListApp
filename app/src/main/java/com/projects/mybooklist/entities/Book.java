package com.projects.mybooklist.entities;

public class Book {

    private String title;
    private String body;
    private float rating;
    private MyRGB placeholderColor;
    private String url;

    public Book() {
    }

    public Book(String title, String body, float rating, MyRGB placeholderColor, String url) {
        this.title = title;
        this.body = body;
        this.rating = rating;
        this.placeholderColor = placeholderColor;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public MyRGB getPlaceholderColor() {
        return placeholderColor;
    }

    public void setPlaceholderColor(MyRGB placeholderColor) {
        this.placeholderColor = placeholderColor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + title + '\'' +
                ", author='" + body + '\'' +
                ", rank=" + rating +
                ", placeholderColor=" + placeholderColor +
                ", imageUrl='" + url + '\'' +
                '}';
    }
}
