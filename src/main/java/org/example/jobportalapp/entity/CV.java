package org.example.jobportalapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cvs")
public class CV extends BaseEntity {
    private String fileName;
    private String filePath;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}