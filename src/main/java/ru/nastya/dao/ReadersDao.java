package ru.nastya.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nastya.models.Reader;

import java.time.Period;
import java.util.List;


@Component
public class ReadersDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReadersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reader> index() {
        return jdbcTemplate.query("SELECT * FROM readers", new BeanPropertyRowMapper<>(Reader.class));
    }

    public Reader showReader(int id) {
        return jdbcTemplate.query("SELECT * FROM readers WHERE reader_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Reader.class)).stream().findAny().orElse(null);
    }

    public void addReaders(Reader reader) {
        jdbcTemplate.update("INSERT INTO readers (surname, name, middle_name, year_of_birth) VALUES (?, ?, ?, ?)",
                reader.getSurname(), reader.getName(), reader.getMiddleName(), reader.getYearOfBirth());

    }

    public void deleteReader(int id) {
        jdbcTemplate.update("DELETE FROM readers WHERE reader_id=?", id);
    }

    public void editReader(Reader updateReader, int id) {
        jdbcTemplate.update("UPDATE readers SET surname=?, name=?, middle_name=?, year_of_birth=? WHERE reader_id=?",
                updateReader.getSurname(), updateReader.getName(), updateReader.getMiddleName(), updateReader.getYearOfBirth(), id);


    }

}