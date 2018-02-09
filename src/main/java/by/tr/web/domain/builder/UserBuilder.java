package by.tr.web.domain.builder;

import by.tr.web.domain.BanInfo;
import by.tr.web.domain.Review;
import by.tr.web.domain.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserBuilder {
    private int id;

    private String userName;
    private String email;
    private String password;

    private User.UserStatus userStatus;
    private boolean isBanned;
    private BanInfo banInfo;

    private Timestamp registrationDate;
    private List<Review> reviews;
    public UserBuilder(){
        banInfo = new BanInfo();
        userStatus = User.UserStatus.CASUAL_VIEWER;
        reviews = new ArrayList<>();
    }

    public UserBuilder addId(int id) {
        this.id = id;
        return this;
    }

    public UserBuilder addUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public UserBuilder addEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder addPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder addUserStatus(String userStatus) {
        this.userStatus = User.UserStatus.valueOf(userStatus.toUpperCase());
        return this;
    }

    public UserBuilder addBanStatus(boolean banStatus) {
        isBanned = banStatus;
        return this;
    }

    public UserBuilder addBanInfo(BanInfo banInfo) {
        this.banInfo = banInfo;
        return this;
    }

    public UserBuilder addRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public UserBuilder addUserReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }
    public User create(){
        User user = new User();

        user.setId(id);
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);

        user.setStatus(userStatus);
        user.setIsBanned(isBanned);
        user.setBanInfo(banInfo);

        user.setRegistrationDate(registrationDate);
        user.setReviews(reviews);

        return user;
    }
}
