package net.dzakirin.authservice.repository;

import net.dzakirin.authservice.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<Token, UUID> {

//    @Query(value = """
//      select t from Token t inner join User u
//      on t.user.id = u.id
//      where u.id = :id and (t.expired = false or t.revoked = false)
//      """)
//    List<Token> findAllValidTokenByUser(UUID id);

    Optional<Token> findByToken(String token);
}

