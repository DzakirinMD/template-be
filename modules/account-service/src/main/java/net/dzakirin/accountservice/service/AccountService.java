package net.dzakirin.accountservice.service;

import net.dzakirin.accountservice.constant.TransactionType;
import net.dzakirin.accountservice.dto.request.AccountTransactionDto;
import net.dzakirin.accountservice.dto.request.CreateAccountDto;
import net.dzakirin.accountservice.dto.request.TransferRequestDto;
import net.dzakirin.accountservice.dto.response.AccountDto;
import net.dzakirin.accountservice.dto.response.TransactionDto;
import net.dzakirin.accountservice.dto.response.UserDto;
import net.dzakirin.accountservice.exception.InsufficientFundsException;
import net.dzakirin.accountservice.exception.ResourceNotFoundException;
import net.dzakirin.accountservice.mapper.AccountMapper;
import net.dzakirin.accountservice.mapper.TransactionMapper;
import net.dzakirin.accountservice.mapper.UserMapper;
import net.dzakirin.accountservice.model.AccountEntity;
import net.dzakirin.accountservice.model.TransactionEntity;
import net.dzakirin.accountservice.producer.AccountDataChangedProducer;
import net.dzakirin.accountservice.producer.TransactionDataChangedProducer;
import net.dzakirin.accountservice.repo.AccountRepo;
import net.dzakirin.accountservice.repo.TransactionRepo;
import net.dzakirin.accountservice.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static net.dzakirin.accountservice.constant.ErrorCodes.ACCOUNT_NOT_FOUND;
import static net.dzakirin.accountservice.constant.ErrorCodes.USER_NOT_FOUND;

@Service
public class AccountService {

    private static final Random random = new Random();

    private final UserRepo userRepo;
    private final AccountRepo accountRepo;
    private final TransactionService transactionService;
    private final AccountDataChangedProducer accountDataChangedProducer;

    public AccountService(
            UserRepo userRepo,
            AccountRepo accountRepo,
            TransactionService transactionService,
            AccountDataChangedProducer accountDataChangedProducer
    ) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.transactionService = transactionService;
        this.accountDataChangedProducer = accountDataChangedProducer;
    }

    public AccountDto getAccount(String accountNumber) {
        var account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND.getMessage()));
        return AccountMapper.toAccountDto(account);
    }

    @Transactional
    public UserDto createAccountForUser(CreateAccountDto createAccountDto) {
        var user = userRepo.findById(UUID.fromString(createAccountDto.getUserId()))
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND + createAccountDto.getUserId()));

        var newAccount = new AccountEntity();
        newAccount.setUser(user);
        newAccount.setAccountNumber(generateAccountNumber());
        newAccount.setBalance(createAccountDto.getInitialBalance());
        accountRepo.save(newAccount);

        user.getAccounts().add(newAccount);
        return UserMapper.toUserDto(user);
    }

    @Transactional
    public AccountDto createDeposit(AccountTransactionDto accountTransactionDto) {
        var account = accountRepo.findByAccountNumber(accountTransactionDto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND.getMessage(accountTransactionDto.getAccountNumber())));

        // Update account balance
        account.setBalance(account.getBalance().add(accountTransactionDto.getAmount()));
        accountRepo.save(account);

        // Record transaction
        transactionService.recordTransaction(account, accountTransactionDto, TransactionType.DEPOSIT);

        // Publish Deposit Event
        var accountDto = AccountMapper.toAccountDto(account);
        accountDataChangedProducer.publishEvent(accountDto.getAccountNumber(), accountDto, TransactionType.DEPOSIT.getEventName());
        return AccountMapper.toAccountDto(account);
    }

    @Transactional
    public AccountDto createWithdrawal(AccountTransactionDto accountTransactionDto) {
        var account = accountRepo.findByAccountNumber(accountTransactionDto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND.getMessage(accountTransactionDto.getAccountNumber())));

        // Check for sufficient balance
        if (account.getBalance().compareTo(accountTransactionDto.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal.");
        }

        // Update account balance
        account.setBalance(account.getBalance().subtract(accountTransactionDto.getAmount()));
        accountRepo.save(account);

        // Record transaction
        transactionService.recordTransaction(account, accountTransactionDto, TransactionType.WITHDRAWAL);

        // Publish Withdrawal Event
        var accountDto = AccountMapper.toAccountDto(account);
        accountDataChangedProducer.publishEvent(accountDto.getAccountNumber(), accountDto, TransactionType.WITHDRAWAL.getEventName());
        return accountDto;
    }

    @Transactional
    public TransactionDto transferMoney(TransferRequestDto transferRequestDto) {
        var sourceAccount = findByAccountNumber(transferRequestDto.getSourceAccountNumber());
        var destinationAccount = findByAccountNumber(transferRequestDto.getDestinationAccountNumber());

        if (sourceAccount.getBalance().compareTo(transferRequestDto.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds for transfer.");
        }

        // Update account balances
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferRequestDto.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(transferRequestDto.getAmount()));

        accountRepo.save(sourceAccount);
        accountRepo.save(destinationAccount);

        // Record transaction
        var transactionDto = transactionService.recordTransaction(sourceAccount, destinationAccount, transferRequestDto.getAmount());

        // Publish Transaction Event
        transactionService.publishTransactionDataChangedEvent(sourceAccount, transactionDto);

        return transactionDto;
    }

    private String generateAccountNumber() {
        int randomNumber = random.nextInt(99999999) + 1; // Ensures the number is between 1 and 99999999
        return "ACC" + String.format("%08d", randomNumber); // Ensures the number is always 8 digits
    }

    private AccountEntity findByAccountNumber(String accountNumber) {
        return accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND.getMessage(accountNumber)));
    }
}
