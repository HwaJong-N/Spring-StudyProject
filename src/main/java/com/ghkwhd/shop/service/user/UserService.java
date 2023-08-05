package com.ghkwhd.shop.service.user;

import com.ghkwhd.shop.domain.user.User;
import com.ghkwhd.shop.repository.user.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public boolean isExistId(String id) {
        List<User> byId = userRepository.findById(id);
        return byId.size() != 0;
    }

}
