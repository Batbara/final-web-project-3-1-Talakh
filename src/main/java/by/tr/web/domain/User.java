package by.tr.web.domain;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -8141596548978008167L;

    private String userName;
    private String eMail;
    private Password password;
    private UserStatus userStatus;
    private boolean isBanned;
    private int id;

    public User(){
        userName="";
        eMail="";
        password = new Password();
    }
    public User(String userName, String password, String eMail, UserStatus userStatus){
        setUserName(userName);
        setPassword(password);
        seteMail(eMail);
        setStatus(userStatus);
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
        return password.toString();
    }

    public void setPassword(String plainPassword) {
        this.password.setPassword(plainPassword);
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = UserStatus.valueOf(userStatus);
    }
    public void setStatus(UserStatus userStatus){
        this.userStatus = userStatus;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return result;
    }
}
