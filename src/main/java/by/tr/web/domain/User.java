package by.tr.web.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private static final long serialVersionUID = -8141596548978008167L;

    private int id;

    private String userName;
    private String email;
    private String password;

    private UserStatus userStatus;
    private boolean isBanned;
    private BanInfo banInfo;

    private Timestamp registrationDate;
    private List<Review> reviews;

    public enum UserStatus {
        ADMIN, CASUAL_VIEWER, MOVIE_FAN, REVIEWER, CRITIC
    }

    public User(int userId){
        this();
        this.id = userId;
    }
    public User() {
        banInfo = new BanInfo();
        userStatus = UserStatus.CASUAL_VIEWER;
        reviews = new ArrayList<>();
    }

    public User(String userName, String password, String email) {
        this();
        setUserName(userName);
        setPassword(password);
        setEmail(email);
        setStatus(UserStatus.CASUAL_VIEWER);
    }

    public User(int userID, String userName, String eMail, String userStatus, boolean isBanned, Timestamp registrationDate) {
        this();
        setId(userID);
        setUserName(userName);
        setEmail(eMail);
        setUserStatus(userStatus);
        setIsBanned(isBanned);
        setRegistrationDate(registrationDate);
    }

    public BanInfo getBanInfo() {
        return banInfo;
    }

    public void setBanInfo(BanInfo banInfo) {
        this.banInfo = banInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String plainPassword) {
        this.password = plainPassword;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = UserStatus.valueOf(userStatus.toUpperCase());
    }

    public void setStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean banned) {
        isBanned = banned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addUserReview(Review review) {
        reviews.add(review);
    }

    public int getUserRateForShow (int showId){
        int userRate = 0;
        for(Review review : reviews){
            if(review.getShowId() == showId){
                return review.getUserRate();
            }
        }
        return userRate;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (isBanned != user.isBanned) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (userStatus != user.userStatus) return false;
        if (banInfo != null ? !banInfo.equals(user.banInfo) : user.banInfo != null) return false;
        if (registrationDate != null ? !registrationDate.equals(user.registrationDate) : user.registrationDate != null)
            return false;

        return reviews != null ? reviews.equals(user.reviews) : user.reviews == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (userStatus != null ? userStatus.hashCode() : 0);
        result = 31 * result + (isBanned ? 1 : 0);
        result = 31 * result + (banInfo != null ? banInfo.hashCode() : 0);
        result = 31 * result + (registrationDate != null ? registrationDate.hashCode() : 0);
        result = 31 * result + (reviews != null ? reviews.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userStatus=" + userStatus +
                ", isBanned=" + isBanned +
                ", banInfo=" + banInfo +
                ", registrationDate=" + registrationDate +
                ", userReviews=" + reviews +
                '}';
    }
}
