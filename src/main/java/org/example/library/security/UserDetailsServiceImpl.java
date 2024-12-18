package org.example.library.security;

import lombok.RequiredArgsConstructor;
import org.example.library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(
                repository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("Користувача не знайдено!"))
        );
    }
}
