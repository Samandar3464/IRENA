package com.example.irena.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String newsBody;
    @OneToOne
    private AttachmentEntity titlePhoto;
    @OneToOne
    private AttachmentEntity bodyPhoto;

}
