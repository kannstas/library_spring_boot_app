package ru.nastya.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int bookId;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String bookTitle;
    @Min(1850)
    private int yearOfCreation;

    private boolean isBusy;

    private int readerId;

    public Book(int bookId, String bookTitle, int yearOfCreation, boolean isBusy, int readerId) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.yearOfCreation = yearOfCreation;
        this.isBusy = isBusy;
        this.readerId = readerId;
    }

    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getYearOfCreation() {
        return yearOfCreation;
    }

    public void setYearOfCreation(int yearOfCreation) {
        this.yearOfCreation = yearOfCreation;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }
}