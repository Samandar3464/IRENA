package com.example.irena.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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
    private LocalDateTime happenDate;
    private boolean active=true;
    @OneToOne
    private AttachmentEntity titlePhoto;
    @OneToOne
    private AttachmentEntity bodyPhoto;

}
