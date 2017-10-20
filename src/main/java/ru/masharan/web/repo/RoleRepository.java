package ru.masharan.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.masharan.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {}
