package com.ghkwhd.shop.repository.user;

import com.ghkwhd.shop.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository {

    User save(User user);

    List<User> findById(String id);   // id 중복 검사 용, 예외를 막기 위해 List 반환

}
