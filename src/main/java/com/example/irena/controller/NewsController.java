package com.example.irena.controller;

import com.example.irena.model.NewsRegisterDto;
import com.example.irena.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {

    private final NewsService newsService;

    @PostMapping("/addNews")
    public ResponseEntity<?> addNews(@ModelAttribute NewsRegisterDto newsRegisterDto){
        return newsService.addNews(newsRegisterDto);
    }
}
