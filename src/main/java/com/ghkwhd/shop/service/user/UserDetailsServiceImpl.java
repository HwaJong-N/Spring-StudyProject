package com.ghkwhd.shop.service.user;

import com.ghkwhd.shop.domain.user.User;
import com.ghkwhd.shop.domain.user.UserDetailsImpl;
import com.ghkwhd.shop.repository.user.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final JpaUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자입니다"));

        return new UserDetailsImpl(user);
    }

}
