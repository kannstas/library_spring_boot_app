package ru.nastya.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.nastya.models.Book;
import ru.nastya.models.Reader;

import java.util.List;


@Component
public class ReadersDao {

    private final JdbcTemplate jdbcTemplate;

    RowMapper<Reader> readerRowMapper = (rs, rowNumber) ->
            new Reader(
                    rs.getInt("reader_id"),
                    rs.getString("surname"),
                    rs.getString("name"),
                    rs.getString("middle_name"),
                    rs.getInt("year_of_birth")
            );


    @Autowired
    public ReadersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reader> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM readers", readerRowMapper);
    }

    public Reader showReader(int id) {
        return jdbcTemplate.query(
                        "SELECT * FROM readers WHERE reader_id=?",
                        readerRowMapper,
                        id).stream()
                .findAny()
                .orElse(null);
    }

    public void addReaders(Reader reader) {
        jdbcTemplate.update("INSERT INTO readers (surname, name, middle_name, year_of_birth) VALUES (?,?,?,?)",
                reader.getSurname(), reader.getName(), reader.getMiddleName(), reader.getYearOfBirth());
    }

    public void deleteReader(int id) {
        jdbcTemplate.update("DELETE FROM readers WHERE reader_id=?", id);
    }

    public void editReader(Reader updateReader, int id) {
        jdbcTemplate.update("UPDATE readers SET surname=?, name=?, middle_name=?, year_of_birth=? WHERE reader_id=?",
                updateReader.getSurname(), updateReader.getName(), updateReader.getMiddleName(), updateReader.getYearOfBirth(), id);
    }

    public List<Book> findBorrowedBooks(int id) {
      return jdbcTemplate.query( "SELECT book_title, year_of_creation, surname, name, middle_name " +
                      "FROM books b " +
                      "LEFT JOIN public.books_authors ba ON b.book_id = ba.books_id " +
                      "LEFT JOIN authors a ON ba.author_id = a.author_id" +
                      " WHERE reader_id=? ",
              new BeanPropertyRowMapper<>(Book.class),
              id);
      // как вернуть еще и авторов? Отдельный запрос или в книге создать поле Author и ему присваивать значения?
    }
}
