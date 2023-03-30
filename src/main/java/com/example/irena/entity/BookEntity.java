package com.example.irena.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ISBN;
    private String title;
    private String description;
    @OneToOne
    private AttachmentEntity bookPhoto;
    @OneToOne
    private AttachmentEntity bodyPhoto;
    @OneToOne
    private AttachmentEntity book;

}
