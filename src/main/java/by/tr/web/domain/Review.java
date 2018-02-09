package by.tr.web.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class Review implements Serializable {
    private static final long serialVersionUID = 766802755595181444L;

    private User user;
    private int showId;

    private int userRate;
    private String reviewTitle;
    private String reviewContent;
    private Timestamp postDate;
    private ReviewStatus reviewStatus;
    public enum ReviewStatus {
        POSTED, MODERATED, DELETED, NONE
    }
    public Review() {
        reviewStatus = ReviewStatus.MODERATED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (showId != review.showId) return false;
        if (userRate != review.userRate) return false;
        if (user != null ? !(user.getId() == review.user.getId()) : review.user != null) return false;

        if (reviewContent != null ? !(equalsIgnoreNewLines(reviewContent, review.reviewContent)) : review.reviewContent != null)
            return false;
        return postDate != null ? postDate.equals(review.postDate) : review.postDate == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + showId;
        result = 31 * result + userRate;
        result = 31 * result + (reviewContent != null ? reviewContent.hashCode() : 0);
        result = 31 * result + (postDate != null ? postDate.hashCode() : 0);
        return result;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public int getUserRate() {
        return userRate;
    }

    public void setUserRate(int userRate) {
        this.userRate = userRate;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(Timestamp postDate) {
        this.postDate = postDate;
    }

    public ReviewStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    @Override
    public String toString() {
        return "UserReview{" +
                "userId=" + user.getId() +
                "userNme=" + user.getUserName() +
                ", showId=" + showId +
                ", userRate=" + userRate +
                ", reviewContent='" + reviewContent + '\'' +
                ", postDate=" + postDate +
                ", status=" + reviewStatus +
                '}';
    }
    private boolean equalsIgnoreNewLines(String first, String second){
        String firstWithoutEscapeChars = first.replaceAll("\\r*\\n*", "");
        String secondWithoutEscapeChars = second.replaceAll("\\r*\\n*", "");
        return firstWithoutEscapeChars.equalsIgnoreCase(secondWithoutEscapeChars);
    }
}
