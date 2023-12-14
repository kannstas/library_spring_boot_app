package ru.nastya.models;

public class Book {
    private String title;

    private int yearOfCreation;
    private Author author;

    public Book(String title, int yearOfCreation, Author author) {
        this.title = title;
        this.yearOfCreation = yearOfCreation;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(int yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}