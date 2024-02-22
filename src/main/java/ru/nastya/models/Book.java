package ru.nastya.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Book {
    private int book_id;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String title;
    @Min(1850)
    private int yearOfCreation;


    public Book(int book_id, String title, int yearOfCreation) {
        this.book_id = book_id;
        this.title = title;
        this.yearOfCreation = yearOfCreation;

    }

    public Book() {
    }

    public int getBook_id() { return book_id; }

    public String getTitle() {
        return title;
    }

    public int getYearOfCreation() {
        return yearOfCreation;
    }

}