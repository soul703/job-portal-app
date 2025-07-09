package org.example.jobportalapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.jobportalapp.myEnum.RoleType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType roleName;
}