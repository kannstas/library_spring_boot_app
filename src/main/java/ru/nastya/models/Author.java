package ru.nastya.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Author {

    private int id;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 100, message = "Фамилия не может быть больше 100")
    private String lastName;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 100, message = "Имя не может быть больше 100")
    private String firstName;

    @Size(max = 100, message = "Имя не может быть больше 100")
    private String middleName;

    public Author(int id, String lastName, String firstName, String middleName) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public Author() {
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

}