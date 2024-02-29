package ru.nastya.models;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Reader {
    private int readerId;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 100, message = "Фамилия не может быть больше 100")
    private String surname;

    @NotEmpty(message = "Поле не должно быть пустым")
    @Size(max = 100, message = "Имя не может быть больше 100")
    private String name;
    @Size(max = 100, message = " Поле не может быть больше 100")
    private String middleName;

    @NotNull(message = "Поле не должно быть пустым")
    private int yearOfBirth;


    public Reader(int readerId, String surname, String name, String middleName, int yearOfBirth) {
        this.readerId = readerId;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.yearOfBirth = yearOfBirth;
    }

    public Reader() {

    }

    public int getReaderId() {
        return readerId;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}

