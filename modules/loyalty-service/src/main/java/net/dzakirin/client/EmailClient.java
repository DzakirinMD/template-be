package net.dzakirin.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dzakirin.common.dto.request.EmailRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

import static net.dzakirin.common.constant.AppConstants.HEADER_INTERNAL_API_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailClient {

    @Value("${client.internal.notification-service.baseurl}")
    private String baseUrl;

    @Value("${application.security.internal-api-key}")
    private String internalApiKey;

    private final WebClient webClient;

    public void sendLoyaltyPointsEmail(UUID customerId, String customerEmail, int pointsAwarded) {
        String url = String.format("%s/emails/send-loyalty-points", baseUrl.replaceAll("/$", "")); // Ensure no double slashes

        log.info("Sending Internal loyalty points email to Email Service: {}", url);

        webClient.post()
                .uri(url)
                .header(HEADER_INTERNAL_API_KEY, internalApiKey)
                .bodyValue(new EmailRequest(customerId, customerEmail, pointsAwarded))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(response -> log.info("✅ Loyalty Points Email Sent: CustomerID={}, Points={}", customerId, pointsAwarded))
                .doOnError(error -> log.error("❌ Failed to send Loyalty Points Email: {}", error.getMessage()))
                .subscribe();
    }
}
