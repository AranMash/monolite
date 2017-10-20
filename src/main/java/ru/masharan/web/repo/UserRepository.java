package ru.masharan.web.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.masharan.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User ,Long> {

    User findByName(String name);
}
