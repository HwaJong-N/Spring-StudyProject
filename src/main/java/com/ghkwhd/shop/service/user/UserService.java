package com.ghkwhd.shop.service.user;

import com.ghkwhd.shop.domain.user.User;
import com.ghkwhd.shop.repository.user.UserRepository;
import com.ghkwhd.shop.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User save(UserDTO dto) {
        User user = User.createUser(dto, passwordEncoder);
        return userRepository.save(user);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public boolean isExistId(String id) {
        return this.findById(id).isPresent();
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

}
