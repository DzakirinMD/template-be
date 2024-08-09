package net.dzakirin.cashmanagement.service;

import net.dzakirin.cashmanagement.constant.TransactionType;
import net.dzakirin.cashmanagement.dto.request.CreateAccountDto;
import net.dzakirin.cashmanagement.dto.request.AccountTransactionDto;
import net.dzakirin.cashmanagement.dto.request.TransferRequestDto;
import net.dzakirin.cashmanagement.dto.response.AccountDto;
import net.dzakirin.cashmanagement.dto.response.UserDto;
import net.dzakirin.cashmanagement.exception.InsufficientFundsException;
import net.dzakirin.cashmanagement.exception.ResourceNotFoundException;
import net.dzakirin.cashmanagement.mapper.AccountMapper;
import net.dzakirin.cashmanagement.mapper.UserMapper;
import net.dzakirin.cashmanagement.model.AccountEntity;
import net.dzakirin.cashmanagement.model.TransactionEntity;
import net.dzakirin.cashmanagement.repo.AccountRepo;
import net.dzakirin.cashmanagement.repo.TransactionRepo;
import net.dzakirin.cashmanagement.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static net.dzakirin.cashmanagement.constant.ErrorCodes.ACCOUNT_NOT_FOUND;
import static net.dzakirin.cashmanagement.constant.ErrorCodes.USER_NOT_FOUND;

@Service
public class AccountService {

    private static final Random random = new Random();

    private final UserRepo userRepo;
    private final AccountRepo accountRepo;
    private final TransactionRepo transactionRepo;

    public AccountService(UserRepo userRepo, AccountRepo accountRepo, TransactionRepo transactionRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
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
    public void createDeposit(AccountTransactionDto accountTransactionDto) {
        var account = accountRepo.findByAccountNumber(accountTransactionDto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND.getMessage(accountTransactionDto.getAccountNumber())));

        // Update account balance
        account.setBalance(account.getBalance().add(accountTransactionDto.getAmount()));
        accountRepo.save(account);

        // Record transaction
        var transaction = new TransactionEntity();
        transaction.setAccount(account);
        transaction.setAmount(accountTransactionDto.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transactionRepo.save(transaction);
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
        var transaction = new TransactionEntity();
        transaction.setAccount(account);
        transaction.setAmount(accountTransactionDto.getAmount().negate()); // Negative value for withdrawal
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transactionRepo.save(transaction);

        return AccountMapper.toAccountDto(account);
    }

    @Transactional
    public AccountDto transferMoney(TransferRequestDto transferRequestDto) {
        var sourceAccount = findByAccountNumber(transferRequestDto.getSourceAccountNumber());
        var destinationAccount = findByAccountNumber(transferRequestDto.getDestinationAccountNumber());

        if (sourceAccount.getBalance().compareTo(transferRequestDto.getAmount()) < 0) {
            throw new InsufficientFundsException("Insufficient funds for transfer.");
        }

        // Withdraw from source
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferRequestDto.getAmount()));
        accountRepo.save(sourceAccount);

        // Deposit into destination
        destinationAccount.setBalance(destinationAccount.getBalance().add(transferRequestDto.getAmount()));
        accountRepo.save(destinationAccount);

        // Record transactions (withdrawal and then deposit)
        recordTransaction(sourceAccount, transferRequestDto.getAmount().negate());
        recordTransaction(destinationAccount, transferRequestDto.getAmount());

        return AccountMapper.toAccountDto(sourceAccount);
    }

    private void recordTransaction(AccountEntity account, BigDecimal amount) {
        var transaction = new TransactionEntity();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.TRANSFER);
        transactionRepo.save(transaction);
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
