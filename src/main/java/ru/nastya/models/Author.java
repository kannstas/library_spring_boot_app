package ru.nastya.models;
import jakarta.validation.constraints.Size;

public class Author {
    private int authorId;

    @Size(max = 100, message = "Фамилия не может быть больше 100")
    private String surname;

    @Size(max = 100, message = "Имя не может быть больше 100")
    private String name;

    @Size(max = 100, message = "Имя не может быть больше 100")
    private String middleName;

    public Author(int authorId, String surname, String name, String middleName) {
        this.authorId = authorId;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
    }

    public Author() {
    }


    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
}