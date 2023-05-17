package com.ghkwhd.shop.controller.review;

import com.ghkwhd.shop.domain.review.Review;
import com.ghkwhd.shop.service.review.ReviewService;
import com.ghkwhd.shop.service.reviewDTO.ReviewUpdateDTO;
import com.ghkwhd.shop.service.reviewDTO.ReviewSaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 등록
    @PostMapping("/add")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Review createReview(@RequestBody ReviewSaveDTO dto) {
        return reviewService.save(dto);
    }

    
    // 리뷰 수정
    @PatchMapping("/edit")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String updateReview(@RequestBody ReviewUpdateDTO dto) {
        reviewService.update(dto);
        return "ok";
    }

    // 리뷰 삭제
    @DeleteMapping("/delete/{reviewId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteByReviewId(reviewId);
        return "ok";
    }
}
