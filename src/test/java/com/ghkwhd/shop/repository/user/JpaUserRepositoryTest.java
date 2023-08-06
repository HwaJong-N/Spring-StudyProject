package com.ghkwhd.shop.repository.user;

import com.ghkwhd.shop.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;


@SpringBootTest
@Transactional
class JpaUserRepositoryTest {

    @Autowired
    JpaUserRepository userRepository;


    @Test
    void saveTest() {
        User user = new User("nwj1016", "hj", "123123", "nwj1016@nate.com");

        User savedUser = userRepository.save(user);

        // User findUser = userRepository.findById(savedUser.getId()).get();

    }

}