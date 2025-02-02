package net.dzakirin.notificationprocessor.service;

import lombok.extern.slf4j.Slf4j;
import net.dzakirin.notificationprocessor.dto.event.AccountDto;
import net.dzakirin.notificationprocessor.model.EmailDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class ProcessingService {

    private final EmailService emailService;

    public ProcessingService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void processAccountDataChanged(AccountDto accountDto) {
        log.info("Processing account data: AccountNumber={}, Balance={}",
                accountDto.getAccountNumber(),
                accountDto.getBalance());

        if (accountDto.getBalance().compareTo(new BigDecimal(200)) > 0) {
            log.info("Balance is greater than 200. No action required.");
            return;
        }

        // TODO get emailDetails from calling user-service via reactiveWebclient. But now just hardcoded
        // Construct email message
        String msgBody = """
                Hi,
                
                Your account balance is running low. Below are the details:
                
                Account Number: %s
                Current Balance: RM %.2f
                Threshold Limit: RM 200.00
                
                Please take the necessary actions to maintain your account balance.
                
                Thank you.
                """.formatted(accountDto.getAccountNumber(), accountDto.getBalance());
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient("recipient@gmail.com")
                .subject("⚠️ Account Balance Low Alert!")
                .msgBody(msgBody)
                .build();

        emailService.sendEmail(emailDetails);
        log.info("Low balance email sent to {}", emailDetails.getRecipient());
    }
}
