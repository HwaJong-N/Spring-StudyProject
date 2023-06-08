package com.ghkwhd.shop.repository.review;

import com.ghkwhd.shop.domain.item.Item;
import com.ghkwhd.shop.domain.review.Review;
import com.ghkwhd.shop.repository.item.JpaItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class JpaReviewRepositoryTest {

    @Autowired
    JpaItemRepository itemRepository;

    @Autowired
    JpaReviewRepository reviewRepository;

    @Test
    void repositoryFindAllTest() {

        Item item = new Item("test1", 12000, "seller1", "content1",
                "", "defaultImage.jpg", 0.0);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        Review review1 = new Review(item, 5, "nickname1", "comment1");
        reviewRepository.save(review1);

        Review review2 = new Review(item, 3, "nickname2", "comment2");
        reviewRepository.save(review2);



        List<Review> reviews = reviewRepository.findAll(item);


        assertThat(reviews).containsExactly(review1, review2);
    }


    @Test
    void calAvgStarTest() {
        Item item = new Item("test1", 12000, "seller1", "content1",
                "", "defaultImage.jpg", 0.0);
        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        Review review1 = new Review(item, 5, "nickname1", "comment1");
        reviewRepository.save(review1);

        Review review2 = new Review(item, 4, "nickname2", "comment2");
        reviewRepository.save(review2);


        Double avg = reviewRepository.calAvgStar(item);


        assertThat(avg).isEqualTo(4.5);
    }

}