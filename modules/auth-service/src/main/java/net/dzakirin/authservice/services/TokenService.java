package net.dzakirin.authservice.services;

import lombok.RequiredArgsConstructor;
import net.dzakirin.authservice.constant.TokenType;
import net.dzakirin.authservice.dto.UserDto;
import net.dzakirin.authservice.model.Token;
import net.dzakirin.authservice.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;

    public void revokeAllUserTokens(UUID userId) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(userId);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void saveUserToken(UUID userId, String jwtToken) {
        var token = Token.builder()
                .userId(userId)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
