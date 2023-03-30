package com.example.irena.controller;

import com.example.irena.model.BookRegisterDto;
import com.example.irena.model.NewsRegisterDto;
import com.example.irena.service.BookService;
import com.example.irena.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<?> addNews(@ModelAttribute BookRegisterDto bookRegisterDto){
        return bookService.addBook(bookRegisterDto);
    }
}
