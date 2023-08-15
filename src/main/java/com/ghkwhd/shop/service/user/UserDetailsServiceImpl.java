package com.ghkwhd.shop.service.user;

import com.ghkwhd.shop.domain.user.UserDetailsImpl;
import com.ghkwhd.shop.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return userRepository.findById(id)
                .map(user -> new UserDetailsImpl(user, Collections.singleton(new SimpleGrantedAuthority(user.getRole().getValue()))))
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자입니다"));

    }
}
