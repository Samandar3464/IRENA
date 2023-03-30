package com.example.irena.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String originName;
    private Long size;
    private String type;
    private String ContentType;
    private String path;
    private Double duration;
    private LocalDateTime createdDate = LocalDateTime.now();
}
