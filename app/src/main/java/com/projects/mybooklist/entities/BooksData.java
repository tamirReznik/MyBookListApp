package com.projects.mybooklist.entities;

public class BooksData {
    private Book[] data;

    public BooksData() {
    }

    public BooksData(Book[] data) {
        this.data = data;
    }

    public Book[] getData() {
        return data;
    }

    public void setData(Book[] data) {
        this.data = data;
    }
}
