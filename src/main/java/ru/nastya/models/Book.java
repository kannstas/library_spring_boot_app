package ru.nastya.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int bookId;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String bookTitle;
    @Min(1850)
    private int yearOfCreation;


    public Book(int bookId, String bookTitle, int yearOfCreation) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.yearOfCreation = yearOfCreation;

    }

    public Book() {
    }

    public int getBookId() { return bookId; }

    public String getBookTitle() {
        return bookTitle;
    }

    public int getYearOfCreation() {
        return yearOfCreation;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setYearOfCreation(int yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }
}