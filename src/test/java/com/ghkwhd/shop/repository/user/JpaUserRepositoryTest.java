package com.ghkwhd.shop.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;


@SpringBootTest
@Transactional
class JpaUserRepositoryTest {

    @Autowired
    JpaUserRepository userRepository;


}