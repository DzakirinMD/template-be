package net.dzakirin.authservice.webclient;

import lombok.RequiredArgsConstructor;
import net.dzakirin.authservice.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class UserServiceClient {

    private final WebClient authServiceWebClient;

    // If the username might contain special characters, it would be a good idea to URL-encode it to avoid issues.
    public UserDto findByUsername(String username) {
        String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8);
        return authServiceWebClient
                .get()
                .uri(String.format("/users/%s", encodedUsername))
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }
}
