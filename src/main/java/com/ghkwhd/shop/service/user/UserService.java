package com.ghkwhd.shop.service.user;

import com.ghkwhd.shop.domain.user.User;
import com.ghkwhd.shop.repository.user.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final JpaUserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public User save(User user) throws Exception {
        User hashedUser = user.hashPassword(bCryptPasswordEncoder);
        return userRepository.save(hashedUser);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }


    public boolean isExistId(String id) {
        Optional<User> findUser = userRepository.findById(id);
        return findUser.isPresent();
    }

}
