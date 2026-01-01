package net.dzakirin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dzakirin.common.dto.request.EmailRequest;
import net.dzakirin.service.OrderProcessingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static net.dzakirin.common.constant.AppConstants.HEADER_INTERNAL_API_KEY;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/emails")
public class EmailController {

    @Value("${application.security.internal-api-key}")
    private String internalApiKey;

    private final OrderProcessingService orderProcessingService;

    @PostMapping("/send-loyalty-points")
    public void sendLoyaltyPointsEmail(
            @RequestHeader(HEADER_INTERNAL_API_KEY) String secretKey,
            @RequestBody EmailRequest emailRequest) {
        log.info("Received request to send loyalty points email: {}", emailRequest);

        // Manual Security Check
        if (!internalApiKey.equals(secretKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Internal Key");
        }

        orderProcessingService.sendLoyaltyPointsEmail(emailRequest);
    }
}
