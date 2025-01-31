package net.dzakirin.cashmanagement.repo;

import net.dzakirin.cashmanagement.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}
