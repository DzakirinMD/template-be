package net.dzakirin.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dzakirin.common.dto.request.SignupRequest;
import net.dzakirin.common.dto.response.SignupResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static net.dzakirin.common.constant.AppConstants.HEADER_INTERNAL_API_KEY;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserClient {

    @Value("${client.internal.user-service.baseurl}")
    private String baseUrl;

    @Value("${application.security.internal-api-key}")
    private String internalApiKey;

    private final WebClient webClient;

    public SignupResponse createUser(SignupRequest request) {
        String url = String.format("%s/api/users/internal/create", baseUrl.replaceAll("/$", ""));

        log.info("Sending Internal Create Request to User Service: {}", url);

        return webClient.post()
                .uri(url)
                .header(HEADER_INTERNAL_API_KEY, internalApiKey)
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> {
                                    log.error("User Service Failed: {}", errorBody);
                                    // Throwing RuntimeException here triggers the @Transactional rollback in AuthService
                                    return Mono.error(new RuntimeException("Failed to create user profile: " + errorBody));
                                })
                )
                .bodyToMono(SignupResponse.class)
                .doOnSuccess(resp -> log.info("✅ User Profile Created: Email={}, Name={}", resp.getEmail(), resp.getFirstName()))
                .block(); // Blocking is required to keep the transaction context alive
    }
}