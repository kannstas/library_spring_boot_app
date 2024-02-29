package ru.nastya.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.nastya.models.Author;
import ru.nastya.models.Book;
import ru.nastya.models.Reader;

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

    RowMapper<Book> bookRowMapper = (rs, rowNumber) ->
            new Book(
                    rs.getInt("book_id"),
                    rs.getString("book_title"),
                    rs.getInt("year_of_creation"),
                    rs.getBoolean("is_busy"),
                    rs.getInt("reader_id")
            );


    public BooksDao(JdbcTemplate jdbcTemplate) throws SQLException {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAll() {
        return jdbcTemplate.query("SELECT * FROM books", bookRowMapper);
    }

    public Author showAuthor(int id) {
        return jdbcTemplate.query("SELECT a.surname, a.name, a.middle_name " +
                        "FROM authors a" +
                        " LEFT JOIN public.books_authors ba on a.author_id = ba.author_id " +
                        "LEFT JOIN books ON ba.books_id = books.book_id WHERE book_id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Author.class)).stream().findAny().orElse(null);
    }

    public Book showBook(int id) {
        return jdbcTemplate.query("SELECT * FROM books WHERE book_id=?",
                bookRowMapper,
                id).stream()
                .findAny()
                .orElse(null);
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
                    bookId, book.getBookTitle(), book.getYearOfCreation(), false, null);
            jdbcTemplate.update("INSERT INTO books_authors (books_id, author_id) VALUES (?,?)",
                    bookId, authorId);
        } else {
            jdbcTemplate.update("INSERT INTO books (book_id,book_title, year_of_creation, is_busy, reader_id) VALUES (?, ?, ?, ?, ?)",
                    bookId, book.getBookTitle(), book.getYearOfCreation(), true, 13);
        }
    }

    public void editBook(Book updateBook, int id) {
        jdbcTemplate.update("UPDATE books SET book_title=?, year_of_creation=? WHERE book_id=?",
                updateBook.getBookTitle(), updateBook.getYearOfCreation(), id);
    }

    public void editAuthor(Author author) {
        jdbcTemplate.update("UPDATE authors SET surname=?, name=?, middle_name=?",
                author.getSurname(), author.getName(), author.getMiddleName());
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM books_authors WHERE books_id=?", id);
        jdbcTemplate.update("DELETE FROM books WHERE book_id=?", id);
    }

    public void releaseBook (int id) {
        jdbcTemplate.update("UPDATE books SET is_busy=false, reader_id = null WHERE book_id=?", id);
    }

    public void assignReader (int id, Reader designatedReader) {
        jdbcTemplate.update("UPDATE books SET reader_id=?, is_busy=true WHERE book_id =?", designatedReader.getReaderId(), id);
    }

    public Reader findReader (int id) {
       return jdbcTemplate.query("SELECT surname, name, middle_name " +
                               "FROM books " +
                               "JOIN readers ON books.reader_id=readers.reader_id " +
                               "WHERE book_id=?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Reader.class)).stream()
                .findAny()
                .orElse(null);
    }
}