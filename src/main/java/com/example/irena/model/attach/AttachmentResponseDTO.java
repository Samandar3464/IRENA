package com.example.irena.model.attach;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class AttachmentResponseDTO {
    private String id;
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private Double duration;
    private LocalDateTime createdData;
    private String url;
}
