package ru.nastya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.nastya.dao.BooksDao;
import ru.nastya.dao.ReadersDao;
import ru.nastya.models.Author;
import ru.nastya.models.Book;
import ru.nastya.models.Reader;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    BooksDao booksDao;
    ReadersDao readersDao;

    @Autowired
    public BooksController(BooksDao booksDao, ReadersDao readersDao) {
        this.booksDao = booksDao;
        this.readersDao = readersDao;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping()
    public String getAll(Model model) {
        List<Book> books = booksDao.getAll();
        model.addAttribute("books", books);
        return "book/index";
    }

    @GetMapping("/{id}")
    public String showBook(Model model, @ModelAttribute("reader") Reader reader,
                           @PathVariable("id") int id) {
        Book book = booksDao.showBook(id);
        model.addAttribute("book", book);
        model.addAttribute("author", booksDao.showAuthor(id));
        model.addAttribute("owner", booksDao.findReader(id));
        model.addAttribute("readers", readersDao.getAll());
        return "book/showBook";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book,
                          @ModelAttribute("author") Author author) {
        return "book/create";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model,
                           @PathVariable("id") int id) {
        model.addAttribute("book", booksDao.showBook(id));
        model.addAttribute("author", booksDao.showAuthor(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") Book book,
                             @ModelAttribute("author") Author author,
                             BindingResult result,
                             @PathVariable("id") int id) {
        if (result.hasErrors()) {
            return "redirect:readers/new";
        }
        booksDao.editBook(book, id);
        if (author.getName() != null && author.getSurname() != null) {
            booksDao.editAuthor(author);
        }
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        booksDao.releaseBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignReader(@PathVariable("id") int id, @ModelAttribute Reader designatedReader) {
        booksDao.assignReader(id, designatedReader);
        return "redirect:/books/" + id;
    }

    ////    @Transactional

    @PostMapping()
    public String addBook(
            @ModelAttribute("author") Author author,
            @ModelAttribute("book") Book book,
            BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/new";
        }
        try {
            booksDao.addBook(author, book);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBooks(@PathVariable("id") int id) {
        booksDao.deleteBook(id);
        return "redirect:/books";
    }

}