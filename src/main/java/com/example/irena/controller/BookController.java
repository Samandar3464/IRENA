package com.example.irena.controller;

import com.example.irena.model.request.BookRegisterDto;
import com.example.irena.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book")
public class BookController {

    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<?> addNews(@ModelAttribute BookRegisterDto bookRegisterDto){
        return bookService.addBook(bookRegisterDto);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBook(@PathVariable  int id){
        return bookService.getBook(id);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable  int id){
        return bookService.delete(id);
    }
}
