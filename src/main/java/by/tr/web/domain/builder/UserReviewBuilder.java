package by.tr.web.domain.builder;

import by.tr.web.domain.Review;
import by.tr.web.domain.User;

import java.sql.Timestamp;

public class UserReviewBuilder {

    private User user;
    private int showId;

    private int userRate;

    private String reviewTitle;
    private String reviewContent;
    private Timestamp postDate;

    private Review.ReviewStatus reviewStatus;

    public UserReviewBuilder(){
        user = new User();
        reviewStatus = Review.ReviewStatus.MODERATED;
    }

    public UserReviewBuilder addUser(User user) {
        this.user = user;
        return this;
    }

    public UserReviewBuilder addShowId(int showId) {
        this.showId = showId;
        return this;
    }

    public UserReviewBuilder addUserRate(int userRate) {
        this.userRate = userRate;
        return this;
    }
    public UserReviewBuilder addReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
        return this;
    }
    public UserReviewBuilder addReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
        return this;
    }

    public UserReviewBuilder addPostDate(Timestamp postDate) {
        this.postDate = postDate;
        return this;
    }
    public UserReviewBuilder addReviewStatus(String reviewStatus) {
        this.reviewStatus = Review.ReviewStatus.valueOf(reviewStatus.toUpperCase());
        return this;
    }
    public Review create(){
        Review review = new Review();

        review.setShowId(showId);
        review.setUser(user);

        review.setUserRate(userRate);
        review.setPostDate(postDate);
        review.setReviewTitle(reviewTitle);
        review.setReviewContent(reviewContent);
        review.setReviewStatus(reviewStatus);

        return review;
    }
}
