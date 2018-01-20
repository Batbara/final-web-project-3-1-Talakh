package by.tr.web.domain;

import java.io.Serializable;

public class BanReason implements Serializable {

    private static final long serialVersionUID = 4272170207416911228L;
    private int id;
    private String reason;

    public BanReason() {

    }
    public BanReason(String reason){
        this.reason = reason;
    }

    public BanReason(int id) {
        this.id = id;
    }

    public BanReason(int id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BanReason banReason = (BanReason) o;

        if (id != banReason.id) return false;
        return reason != null ? reason.equals(banReason.reason) : banReason.reason == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BanReason{" +
                "id=" + id +
                ", reason='" + reason + '\'' +
                '}';
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
