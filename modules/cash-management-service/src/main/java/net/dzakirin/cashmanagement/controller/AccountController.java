package net.dzakirin.cashmanagement.controller;

import net.dzakirin.cashmanagement.dto.request.CreateAccountDto;
import net.dzakirin.cashmanagement.dto.request.AccountTransactionDto;
import net.dzakirin.cashmanagement.dto.request.TransferRequestDto;
import net.dzakirin.cashmanagement.dto.response.AccountDto;
import net.dzakirin.cashmanagement.dto.response.UserDto;
import net.dzakirin.cashmanagement.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createAccountForUser(@RequestBody CreateAccountDto createAccountDto) {
       var userDto = accountService.createAccountForUser(createAccountDto);
       return ResponseEntity.ok(userDto);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> createDeposit(@RequestBody AccountTransactionDto accountTransactionDto) {
        accountService.createDeposit(accountTransactionDto);
        return ResponseEntity.status(204).build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDto> createWithdrawal(@RequestBody AccountTransactionDto accountTransactionDto) {
        var accountDto = accountService.createWithdrawal(accountTransactionDto);
        return ResponseEntity.ok(accountDto);
    }

    @PostMapping("/transfer")
    public ResponseEntity<AccountDto> transferMoney(@RequestBody TransferRequestDto transferRequestDto) {
        var accountDto = accountService.transferMoney(transferRequestDto);
        return ResponseEntity.ok(accountDto);
    }
}
