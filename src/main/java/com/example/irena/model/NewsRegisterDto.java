package com.example.irena.model;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsRegisterDto {
    private String tittle;
    private MultipartFile titlePhoto;
    private MultipartFile bodyPhoto;
    private String newsBody;
}
