package ru.masharan.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.masharan.model.entity.EmailVerificationToken;
import ru.masharan.model.entity.User;

public interface TokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    public EmailVerificationToken findByToken(String token);

    public EmailVerificationToken findByUser(User user);
}
