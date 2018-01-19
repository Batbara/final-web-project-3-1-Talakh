package by.tr.web.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private static final long serialVersionUID = -8141596548978008167L;

    private int id;

    private String userName;
    private String eMail;
    private String password;

    private UserStatus userStatus;
    private boolean isBanned;
    private BanInfo banInfo;

    private Timestamp registrationDate;
    private List<UserReview> userReviews;
    public enum UserStatus {
        ADMIN, CASUAL_VIEWER, MOVIE_FAN, REVIEWER, CRITIC
    }

    public User() {
        userName = "";
        eMail = "";
        password = "";
        banInfo = new BanInfo();
        userStatus = UserStatus.CASUAL_VIEWER;
        userReviews = new ArrayList<>();
    }

    public User(String userName, String password, String eMail) {
        setUserName(userName);
        setPassword(password);
        seteMail(eMail);
        setStatus(UserStatus.CASUAL_VIEWER);
    }

    public User(int userID, String userName, String eMail, String userStatus, boolean isBanned, Timestamp registrationDate) {
        setId(userID);
        setUserName(userName);
        seteMail(eMail);
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

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
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

    public List<UserReview> getUserReviews() {
        return userReviews;
    }

    public void setUserReviews(List<UserReview> userReviews) {
        this.userReviews = userReviews;
    }
    public void addUserReview (UserReview review){
        userReviews.add(review);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isBanned != user.isBanned) return false;
        if (id != user.id) return false;
        if (!userName.equals(user.userName)) return false;
        if (!eMail.equals(user.eMail)) return false;
        if (!password.equals(user.password)) return false;
        if (!banInfo.equals(user.banInfo)) return false;
        return userStatus == user.userStatus;
    }

    @Override
    public int hashCode() {
        int result = userName.hashCode();
        result = 31 * result + eMail.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + userStatus.hashCode();
        result = 31 * result + (isBanned ? 1 : 0);
        result = 31 * result + id;
        result = 31 * result + banInfo.hashCode();
        return result;
    }
}
