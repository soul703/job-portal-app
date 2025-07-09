package org.example.jobportalapp.repository;

import org.example.jobportalapp.entity.Role;
import org.example.jobportalapp.myEnum.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleType roleName);
}
