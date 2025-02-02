package net.dzakirin.accountservice.controller;

import net.dzakirin.accountservice.dto.request.CreateAccountDto;
import net.dzakirin.accountservice.dto.request.AccountTransactionDto;
import net.dzakirin.accountservice.dto.request.TransferRequestDto;
import net.dzakirin.accountservice.dto.response.AccountDto;
import net.dzakirin.accountservice.dto.response.TransactionDto;
import net.dzakirin.accountservice.dto.response.UserDto;
import net.dzakirin.accountservice.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createAccountForUser(@RequestBody CreateAccountDto createAccountDto) {
       return ResponseEntity.ok(accountService.createAccountForUser(createAccountDto));
    }

    @PostMapping("/deposit")
    public ResponseEntity<AccountDto> createDeposit(@RequestBody AccountTransactionDto accountTransactionDto) {
        return ResponseEntity.ok(accountService.createDeposit(accountTransactionDto));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDto> createWithdrawal(@RequestBody AccountTransactionDto accountTransactionDto) {
        return ResponseEntity.ok(accountService.createWithdrawal(accountTransactionDto));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionDto> transferMoney(@RequestBody TransferRequestDto transferRequestDto) {
        return ResponseEntity.ok(accountService.transferMoney(transferRequestDto));
    }
}
