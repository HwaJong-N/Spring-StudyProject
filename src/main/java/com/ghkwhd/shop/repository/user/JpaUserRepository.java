package com.ghkwhd.shop.repository.user;

import com.ghkwhd.shop.domain.user.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaUserRepository implements UserRepository{

    private final EntityManager em;

    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public User save(User user) {
        em.persist(user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        String jpql = "select u from User u where u.id=:id";
        TypedQuery<User> query = em.createQuery(jpql, User.class).setParameter("id", id);
        List<User> userList = query.getResultList();
        if (userList.size() == 0) {
            return Optional.empty();
        }
        return Optional.ofNullable(userList.get(0));
    }
}
