package by.tr.web.domain.builder;

import by.tr.web.domain.User;
import by.tr.web.domain.UserReview;

import java.sql.Timestamp;

public class UserReviewBuilder {

    private User user;
    private int showId;

    private int userRate;

    private String reviewContent;
    private Timestamp postDate;

    public UserReviewBuilder(){
        user = new User();
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

    public UserReviewBuilder addReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
        return this;
    }

    public UserReviewBuilder addPostDate(Timestamp postDate) {
        this.postDate = postDate;
        return this;
    }
    public UserReview create(){
        UserReview userReview = new UserReview();

        userReview.setShowId(showId);
        userReview.setUser(user);

        userReview.setUserRate(userRate);
        userReview.setPostDate(postDate);
        userReview.setReviewContent(reviewContent);

        return userReview;
    }
}
