package ru.nastya.dto;

import jakarta.validation.constraints.Size;

public class AuthorDTO {
        @Size(max = 100, message = "Фамилия не может быть больше 100")
        private String surname;

        @Size(max = 100, message = "Имя не может быть больше 100")
        private String name;

        @Size(max = 100, message = "Имя не может быть больше 100")
        private String middleName;
}