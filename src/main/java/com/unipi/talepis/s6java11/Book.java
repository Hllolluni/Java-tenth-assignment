package com.unipi.talepis.s6java11;

public class Book {
    private String title;
    private String author;
    private String description;
    private Integer year;

    public Book(String title, String author, String description, Integer year) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.year = year;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
