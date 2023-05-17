package com.ghkwhd.shop.repository.review;

import com.ghkwhd.shop.domain.item.Item;
import com.ghkwhd.shop.domain.review.Review;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaReviewRepository implements ReviewRepository {

    private final EntityManager em;

    public JpaReviewRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Review save(Review review) {
        em.persist(review);
        return review;
    }

    @Override
    public void update(Long id, Review review) {
        Review findReview = em.find(Review.class, id);
        findReview.setItem(review.getItem());
        findReview.setStar(review.getStar());
        findReview.setNickname(review.getNickname());
        findReview.setComment(review.getComment());
    }

    @Override
    public void deleteByReviewId(Long id) {
        String jpql = "delete from Review r where r.id=:review_id";
        Query query = em.createQuery(jpql).setParameter("review_id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteByItemId(Item item) {
        String jpql = "delete from Review r where r.item=:item";
        Query query = em.createQuery(jpql).setParameter("item", item);
        query.executeUpdate();
    }

    @Override
    public Optional<Review> findById(Long id) {
        Review review = em.find(Review.class, id);
        return Optional.ofNullable(review);
    }

    @Override
    public List<Review> findAll(Item item) {
        String jpql = "select r from Review r where r.item=:item";
        TypedQuery<Review> query = em.createQuery(jpql, Review.class);
        return query.setParameter("item", item).getResultList();
    }
}
