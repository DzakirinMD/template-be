package net.dzakirin.service;

import lombok.RequiredArgsConstructor;
import net.dzakirin.entity.UserCredential;
import net.dzakirin.repository.UserCredentialRepository;
import net.dzakirin.security.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static net.dzakirin.constant.ErrorCodes.EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCredentialRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserCredential user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(EMAIL_NOT_FOUND.getMessage(email)));

        return UserDetailsImpl.build(user);
    }
}