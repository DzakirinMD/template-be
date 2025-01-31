package net.dzakirin.cashmanagement.repo;

import net.dzakirin.cashmanagement.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<TransactionEntity, UUID> {
}
