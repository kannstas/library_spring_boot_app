package ru.nastya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.nastya.dao.BooksDao;
import ru.nastya.models.Author;
import ru.nastya.models.Book;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    BooksDao booksDao;

    @Autowired
    public BooksController(BooksDao booksDao) {
        this.booksDao = booksDao;
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
    public String showBook(Model model,
                           @PathVariable("id") int id) {
        model.addAttribute("book", booksDao.showBook(id));
        model.addAttribute("author", booksDao.showAuthor(id));
        return "book/showBook";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book,
                          @ModelAttribute("author") Author author) {
        return "book/create";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@ModelAttribute("book") Book book,
                           @ModelAttribute("author") Author author,
                           @PathVariable("id") int id) {
        booksDao.showBook(id);
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
//        booksDao.editBook(book,author, id);
        return "redirect:/readers";
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


}