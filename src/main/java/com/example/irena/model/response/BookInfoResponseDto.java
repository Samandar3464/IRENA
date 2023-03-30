package com.example.irena.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoResponseDto {
    private int id;
    private String ISBN;
    private String tittle;
    private String description;
    private MultipartFile bookPhoto;
    private MultipartFile bodyPhoto;
}
