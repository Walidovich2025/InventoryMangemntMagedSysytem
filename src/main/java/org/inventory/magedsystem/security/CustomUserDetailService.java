package org.inventory.magedsystem.security;

import lombok.RequiredArgsConstructor;
import org.inventory.magedsystem.entity.User;
import org.inventory.magedsystem.exceptions.NotFoundException;
import org.inventory.magedsystem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username)
                .orElseThrow(()->new NotFoundException("User Email is Not Found"));
        return AuthUser.builder()
                .user(user)
                .build();
    }
}
