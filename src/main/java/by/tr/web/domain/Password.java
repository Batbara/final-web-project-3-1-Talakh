package by.tr.web.domain;

import org.mindrot.jbcrypt.BCrypt;

import java.io.Serializable;

public class Password implements Serializable {

    private static final long serialVersionUID = -1288050957010070296L;
    private String password;

    public Password(){}

    public String getPassword() {
        return password;
    }

    public void setPassword(String plainTextPassword) {
        this.password = hashPassword(plainTextPassword);
    }

    private String hashPassword(String plainTextPassword) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        String hashedPassword = BCrypt.hashpw(plainTextPassword, salt);

        return (hashedPassword);
    }

    @Override
    public String toString() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Password password1 = (Password) o;

        return password.equals(password1.password);
    }

    @Override
    public int hashCode() {
        return password.hashCode();
    }
}
