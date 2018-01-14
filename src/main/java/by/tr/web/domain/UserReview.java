package by.tr.web.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserReview implements Serializable{
    private static final long serialVersionUID = 766802755595181444L;

    private int userID;
    private String userName;
    private int userRate;
    private String reviewContent;
    private Timestamp postDate;

    public UserReview(){
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

        UserReview that = (UserReview) o;

        if (userID != that.userID) return false;
        if (Double.compare(that.userRate, userRate) != 0) return false;
        if (reviewContent != null ? !reviewContent.equals(that.reviewContent) : that.reviewContent != null)
            return false;
        return postDate != null ? postDate.equals(that.postDate) : that.postDate == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userID;
        temp = Double.doubleToLongBits(userRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (reviewContent != null ? reviewContent.hashCode() : 0);
        result = 31 * result + (postDate != null ? postDate.hashCode() : 0);
        return result;
    }
}
