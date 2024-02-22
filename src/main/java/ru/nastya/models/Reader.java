package ru.nastya.models;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class Reader {

   private int reader_id;

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


    public Reader(int reader_id, String surname, String name, String middleName, int yearOfBirth) {
        this.reader_id = reader_id;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.yearOfBirth = yearOfBirth;
    }

    public Reader () {

    }

    public int getReader_id() {
        return reader_id;
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

}

