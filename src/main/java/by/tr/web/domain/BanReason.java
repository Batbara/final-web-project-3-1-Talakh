package by.tr.web.domain;

import java.io.Serializable;

public class BanReason implements Serializable {

    private static final long serialVersionUID = 4272170207416911228L;
    private int id;
    private String reason;

    public BanReason() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BanReason banReason = (BanReason) o;

        if (id != banReason.id) return false;
        return reason.equals(banReason.reason);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + reason.hashCode();
        return result;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
