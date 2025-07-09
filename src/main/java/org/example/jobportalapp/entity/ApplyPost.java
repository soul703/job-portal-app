package org.example.jobportalapp.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "apply_posts")
public class ApplyPost extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    private Recruitment recruitment;

    private String nameCv;
    private String message;

    @Enumerated(EnumType.STRING)
    private ApplyStatus status;

    public enum ApplyStatus {
        PENDING, APPROVED, REJECTED
    }
}