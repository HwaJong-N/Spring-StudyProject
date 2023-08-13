package com.ghkwhd.shop.repository.user;

import com.ghkwhd.shop.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository {
    User save(User user);

    Optional<User> findById(String id);

    List<User> findAllUsers();

}
