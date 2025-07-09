package org.example.jobportalapp.repository;

import org.example.jobportalapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User,UUID> {
    @Override
    Optional<User> findById(UUID uuid);
    @Override
    @EntityGraph(attributePaths = {"role"})
    Page<User> findAll(Pageable pageable);
    boolean existsByEmail(String email);
}
