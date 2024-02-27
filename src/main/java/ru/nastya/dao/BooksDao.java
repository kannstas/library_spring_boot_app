package ru.nastya.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.nastya.models.Author;
import ru.nastya.models.Book;

import java.sql.*;
import java.util.List;

@Component
public class BooksDao {
    JdbcTemplate jdbcTemplate;
    Connection connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5431/library_db",
            "postgres",
            "postgres"
    );

    public BooksDao(JdbcTemplate jdbcTemplate) throws SQLException {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    public Author showAuthor(int id) {
        return jdbcTemplate.query("SELECT a.surname, a.name, a.middle_name " +
                        "FROM authors a" +
                        " LEFT JOIN public.books_authors ba on a.author_id = ba.author_id " +
                        "LEFT JOIN books ON ba.books_id = books.book_id WHERE book_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Author.class)).stream().findAny().orElse(null);
    }

    public Book showBook(int id) {
        return jdbcTemplate.query("SELECT books.book_title, books.year_of_creation FROM books WHERE book_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    //
    public void addBook(Author author, Book book) throws SQLException {
        Statement statement = connection.createStatement();

        String authorIdQuery = "SELECT pg_catalog.nextval('authors_author_id_seq')";
        ResultSet authorsResultSet = statement.executeQuery(authorIdQuery);
        authorsResultSet.next();
        int authorId = authorsResultSet.getInt(1);

        String bookIdQuery = "SELECT pg_catalog.nextval('books_book_id_seq')";
        ResultSet booksResultSet = statement.executeQuery(bookIdQuery);
        booksResultSet.next();
        int bookId = booksResultSet.getInt(1);

        if (author.getSurname() != null && author.getName() != null) {
            jdbcTemplate.update("INSERT INTO authors(author_id,surname, name, middle_name) VALUES (?, ?, ?, ?)",
                    authorId, author.getSurname(), author.getName(), author.getMiddleName());
            jdbcTemplate.update("INSERT INTO books (book_id,book_title, year_of_creation, is_busy, reader_id) VALUES (?, ?, ?, ?, ?)",
                    bookId, book.getBookTitle(), book.getYearOfCreation(), true, 13);
            jdbcTemplate.update("INSERT INTO books_authors (books_id, author_id) VALUES (?,?)",
                    bookId, authorId);
        } else {
            jdbcTemplate.update("INSERT INTO books (book_id,book_title, year_of_creation, is_busy, reader_id) VALUES (?, ?, ?, ?, ?)",
                    bookId, book.getBookTitle(), book.getYearOfCreation(), true, 13);
        }

    }

    public void editBook(Author author, Book book) {

    }
}