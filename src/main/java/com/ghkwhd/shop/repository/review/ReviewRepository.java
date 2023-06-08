package com.ghkwhd.shop.repository.review;

import com.ghkwhd.shop.domain.item.Item;
import com.ghkwhd.shop.domain.review.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    Review save(Review review);
    void update(Long id, Review review);
    void deleteByReviewId(Long id);
    void deleteByItemId(Item item);
    Optional<Review> findById(Long id);
    List<Review> findAll(Item item);
    Double calAvgStar(Item item);
}
