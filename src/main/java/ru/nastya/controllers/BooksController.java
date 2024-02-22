package ru.nastya.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nastya.dao.BooksDao;
import ru.nastya.models.Author;
import ru.nastya.models.Book;


@Controller
@RequestMapping("/books")
public class BooksController {
    BooksDao booksDao;

    @Autowired
    public BooksController(BooksDao booksDao) {
        this.booksDao = booksDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", booksDao.index());
        return "book/index";
    }
    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksDao.showBook(id));
        return "book/showBook";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book,
                          @ModelAttribute("author") Author author) {
        return "book/create";
    }

//    @Transactional

    @PostMapping()
    public String addBook(@ModelAttribute("book")  Book book,
                          BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/new";
        }
        booksDao.addBook(book);
        return "book/index";
    }

}