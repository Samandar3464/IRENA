package com.example.irena.model.response;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsResponseDto {
    private Long id;
    private String tittle;
    private LocalDateTime happenDate;
    private MultipartFile titlePhoto;
    private MultipartFile bodyPhoto;
    private String newsBody;
}
