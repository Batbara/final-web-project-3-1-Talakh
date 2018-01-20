package by.tr.web.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserReview implements Serializable {
    private static final long serialVersionUID = 766802755595181444L;

    private User user;
    private Show show;

    private int userRate;
    private String reviewContent;
    private Timestamp postDate;

    public UserReview() {
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserReview review = (UserReview) o;

        if (userRate != review.userRate) return false;
        if (user != null ? !user.equals(review.user) : review.user != null) return false;
        if (show != null ? !show.equals(review.show) : review.show != null) return false;
        if (reviewContent != null ? !reviewContent.equals(review.reviewContent) : review.reviewContent != null)
            return false;
        return postDate != null ? postDate.equals(review.postDate) : review.postDate == null;
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (show != null ? show.hashCode() : 0);
        result = 31 * result + userRate;
        result = 31 * result + (reviewContent != null ? reviewContent.hashCode() : 0);
        result = 31 * result + (postDate != null ? postDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserReview{" +
                "user=" + user +
                ", show=" + show +
                ", userRate=" + userRate +
                ", reviewContent='" + reviewContent + '\'' +
                ", postDate=" + postDate +
                '}';
    }
}
