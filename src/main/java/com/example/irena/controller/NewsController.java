package com.example.irena.controller;

import com.example.irena.model.request.NewsRegisterDto;
import com.example.irena.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/news")
public class NewsController {

    private final NewsService newsService;

    @PostMapping("/add")
    public ResponseEntity<?> addNews(@ModelAttribute NewsRegisterDto newsRegisterDto) {
        return newsService.addNews(newsRegisterDto);
    }

    @GetMapping("/getList")
    public ResponseEntity<?> getNewsList(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "happenDate") String sort) {
        return newsService.getNewsList(page, size, sort);
    }

    @GetMapping("/getById")
    public ResponseEntity<?> getNewsById(@RequestParam Long id) {
        return newsService.getNewsById(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteNews(@RequestParam Long id) {
        return newsService.deleteNews(id);
    }
}
