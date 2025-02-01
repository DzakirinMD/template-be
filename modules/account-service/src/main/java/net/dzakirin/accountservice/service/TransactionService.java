package net.dzakirin.accountservice.service;

import net.dzakirin.accountservice.constant.TransactionType;
import net.dzakirin.accountservice.dto.request.AccountTransactionDto;
import net.dzakirin.accountservice.dto.response.TransactionDto;
import net.dzakirin.accountservice.mapper.AccountMapper;
import net.dzakirin.accountservice.mapper.TransactionMapper;
import net.dzakirin.accountservice.model.AccountEntity;
import net.dzakirin.accountservice.model.TransactionEntity;
import net.dzakirin.accountservice.producer.TransactionDataChangedProducer;
import net.dzakirin.accountservice.repo.TransactionRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final TransactionDataChangedProducer transactionDataChangedProducer;

    public TransactionService(
            TransactionRepo transactionRepo,
            TransactionDataChangedProducer transactionDataChangedProducer
    ) {
        this.transactionRepo = transactionRepo;
        this.transactionDataChangedProducer = transactionDataChangedProducer;
    }


    public void recordTransaction(AccountEntity account, AccountTransactionDto accountTransactionDto, TransactionType transactionType) {
        var transaction = new TransactionEntity();
        transaction.setAccount(account);
        transaction.setAmount(accountTransactionDto.getAmount());
        transaction.setTransactionDate(Instant.now());
        transaction.setTransactionType(transactionType);
        transactionRepo.save(transaction);
    }

    public TransactionDto recordTransaction(AccountEntity sourceAccount, AccountEntity destinationAccount, BigDecimal amount) {
        var transaction = new TransactionEntity();
        transaction.setAccount(sourceAccount);
        transaction.setAmount(amount.negate()); // Negative for withdrawal
        transaction.setTransactionDate(Instant.now());
        transaction.setTransactionType(TransactionType.TRANSFER);
        transactionRepo.save(transaction);

        // Update destination account balance
        var depositTransaction = new TransactionEntity();
        depositTransaction.setAccount(destinationAccount);
        depositTransaction.setAmount(amount); // Positive for deposit
        depositTransaction.setTransactionDate(transaction.getTransactionDate()); // Keep same timestamp
        depositTransaction.setTransactionType(TransactionType.TRANSFER);
        transactionRepo.save(depositTransaction);

        return TransactionMapper.toTransactionDto(transaction, sourceAccount, destinationAccount);
    }


    public void publishTransactionDataChangedEvent(AccountEntity sourceAccount, TransactionDto transactionDto) {
        transactionDataChangedProducer.publishEvent(sourceAccount.getAccountNumber(), transactionDto, TransactionType.TRANSFER.getEventName());
    }
}
