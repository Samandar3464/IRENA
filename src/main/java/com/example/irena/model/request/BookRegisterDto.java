package com.example.irena.model.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookRegisterDto {
    private String ISBN;
    private String tittle;
    private String description;
    private MultipartFile bookPhoto;
    private MultipartFile bodyPhoto;
    private MultipartFile book;
}
