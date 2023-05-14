package com.ghkwhd.shop.service.review;

import com.ghkwhd.shop.domain.item.Item;
import com.ghkwhd.shop.domain.review.Review;
import com.ghkwhd.shop.repository.item.JpaItemRepository;
import com.ghkwhd.shop.repository.review.JpaReviewRepository;
import com.ghkwhd.shop.service.reviewDTO.ReviewUpdateDTO;
import com.ghkwhd.shop.service.reviewDTO.ReviewSaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final JpaReviewRepository reviewRepository;
    private final JpaItemRepository itemRepository;

    public Review save(ReviewSaveDTO dto) {

        Item findItem = itemRepository.findById(dto.getItemId()).orElse(null);

        Review review = new Review();
        review.setItem(findItem);
        review.setStar(dto.getStar());
        review.setNickname(dto.getNickname());
        review.setComment(dto.getComment());

        reviewRepository.save(review);

        return review;
    }


    public void update(ReviewUpdateDTO dto) {
        Long id = dto.getId();
        Item item = itemRepository.findById(dto.getItemId()).orElse(null);

        Review review = new Review();
        review.setItem(item);
        review.setStar(dto.getStar());
        review.setNickname(dto.getNickname());
        review.setComment(dto.getComment());

        reviewRepository.update(id, review);
    }

    public void delete(Long id) {
        reviewRepository.delete(id);
    }

    public List<Review> findAll(Long itemId) {
        Item findItem = itemRepository.findById(itemId).orElse(null);
        return reviewRepository.findAll(findItem);
    }
}
