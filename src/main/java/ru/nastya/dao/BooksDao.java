package ru.nastya.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nastya.models.Author;
import ru.nastya.models.Book;
import ru.nastya.models.Reader;

import java.util.List;

@Component
public class BooksDao {
    JdbcTemplate jdbcTemplate;

    public BooksDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Reader> index() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Reader.class));
    }
    public Book showBook(int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE book_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }
    public void addBook(Book book) {
//        jdbcTemplate.update("INSERT INTO authors (surname, name, middle_name) VALUES (?, ?, ?)",
//                author.getLastName(), author.getFirstName(), author.getMiddleName());
        jdbcTemplate.update("INSERT INTO books (book_title, year_of_creation, is_busy, reader_id) VALUES (?, ?)",
                book.getTitle(), book.getYearOfCreation(), true, 0);
//        jdbcTemplate.update("INSERT INTO books_authors (books_id, author_id) VALUES (?,?)",
//                book.getBook_id(), author.getId());

    }
}