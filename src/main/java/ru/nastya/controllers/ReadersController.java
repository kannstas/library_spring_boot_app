package ru.nastya.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.nastya.dao.ReadersDao;
import ru.nastya.models.Reader;

@Controller
@RequestMapping("/readers")
public class ReadersController {

    ReadersDao readersDao;

    @Autowired
    public ReadersController(ReadersDao readersDao) {
        this.readersDao = readersDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("readers", readersDao.index());
        return "reader/index";
    }

    @GetMapping("/{id}")
    public String showReader(@PathVariable("id") int id, Model model) {
        model.addAttribute("reader", readersDao.showReader(id));
        return "reader/showReader";
    }

    @GetMapping("/new")
    public String newReader(@ModelAttribute("reader") Reader reader) {
        return "reader/create";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model,
                       @PathVariable("id") int id) {
        model.addAttribute("reader", readersDao.showReader(id));
        return "reader/edit";
    }

    @PostMapping()
    public String addReader(@ModelAttribute("reader") @Valid Reader reader, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/new";
        }
        readersDao.addReaders(reader);
        return "reader/index";
    }

    @PatchMapping("/{id}")
    public String updateReader(@ModelAttribute("reader") Reader reader,
                               BindingResult result,
                               @PathVariable("id") int id) {
        if (result.hasErrors()) {
            return "reader/edit";
        }
        readersDao.editReader(reader, id);
        return "redirect:/readers";
    }

    @DeleteMapping("/{id}")
    public String deleteReader(@PathVariable("id") int id) {
        readersDao.deleteReader(id);
        return "redirect:/readers";
    }
}