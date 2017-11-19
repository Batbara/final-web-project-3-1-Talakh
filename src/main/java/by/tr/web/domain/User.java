package by.tr.web.domain;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -8141596548978008167L;

    private String userName;
    private String eMail;
    private Password password;
    private Status status;
    private boolean isBanned;
    private int id;

    public User(){
        userName="";
        eMail="";
        password = new Password();
    }
    public User(String userName, String password, String eMail, Status status){
        setUserName(userName);
        setPassword(password);
        seteMail(eMail);
        setStatus(status);
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }
    public void setStatus(Status status){
        this.status = status;
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
}
