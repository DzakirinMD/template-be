package net.dzakirin.userservice.webclient;

import lombok.RequiredArgsConstructor;
import net.dzakirin.userservice.dto.UserDto;
import net.dzakirin.userservice.dto.response.GenerateTokenResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceClient {

    private final WebClient authServiceWebClient;

    public Mono<GenerateTokenResponseDto> generateToken(UserDto userDto) {
        return authServiceWebClient
                .post()
                .uri("/auth/generate-token")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(GenerateTokenResponseDto.class);
    }
}
