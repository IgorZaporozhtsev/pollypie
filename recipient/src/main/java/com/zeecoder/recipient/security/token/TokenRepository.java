package com.zeecoder.recipient.security.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
            SELECT t
            FROM Token t
            INNER JOIN User u
            ON t.user.id = u.id
            WHERE u.id = :id AND (t.expired = false OR t.revoked = false)
            """)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);
}
