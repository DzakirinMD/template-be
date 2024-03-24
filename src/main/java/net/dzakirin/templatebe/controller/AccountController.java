package net.dzakirin.templatebe.controller;

import net.dzakirin.templatebe.dto.request.CreateAccountDto;
import net.dzakirin.templatebe.dto.request.AccountTransactionDto;
import net.dzakirin.templatebe.dto.response.AccountDto;
import net.dzakirin.templatebe.dto.response.UserDto;
import net.dzakirin.templatebe.service.AccountService;
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
}
