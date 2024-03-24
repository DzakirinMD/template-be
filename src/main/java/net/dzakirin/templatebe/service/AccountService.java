package net.dzakirin.templatebe.service;

import net.dzakirin.templatebe.constant.TransactionType;
import net.dzakirin.templatebe.dto.request.CreateAccountDto;
import net.dzakirin.templatebe.dto.response.DepositDto;
import net.dzakirin.templatebe.dto.response.UserDto;
import net.dzakirin.templatebe.exception.ResourceNotFoundException;
import net.dzakirin.templatebe.mapper.UserMapper;
import net.dzakirin.templatebe.model.AccountEntity;
import net.dzakirin.templatebe.model.TransactionEntity;
import net.dzakirin.templatebe.repo.AccountRepo;
import net.dzakirin.templatebe.repo.TransactionRepo;
import net.dzakirin.templatebe.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

import static net.dzakirin.templatebe.constant.ErrorCodes.ACCOUNT_NOT_FOUND;
import static net.dzakirin.templatebe.constant.ErrorCodes.USER_NOT_FOUND;

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
    public void createDeposit(DepositDto depositDto) {
        var account = accountRepo.findByAccountNumber(depositDto.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(ACCOUNT_NOT_FOUND + depositDto.getAccountNumber()));

        // Update account balance
        account.setBalance(account.getBalance().add(depositDto.getAmount()));
        accountRepo.save(account);

        // Record transaction
        var transaction = new TransactionEntity();
        transaction.setAccount(account);
        transaction.setAmount(depositDto.getAmount());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transactionRepo.save(transaction);
    }

    private String generateAccountNumber() {
        int randomNumber = random.nextInt(99999999) + 1; // Ensures the number is between 1 and 99999999
        return "ACC" + String.format("%08d", randomNumber); // Ensures the number is always 8 digits
    }
}