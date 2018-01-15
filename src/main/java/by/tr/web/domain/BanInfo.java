package by.tr.web.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class BanInfo implements Serializable {
    private Timestamp banTime;
    private Timestamp unbanTime;
    private BanReason banReason;

    public BanInfo() {
        banReason = new BanReason();
    }

    public BanInfo(Timestamp banTime, Timestamp unbanTime, BanReason banReason) {
        this.banTime = banTime;
        this.unbanTime = unbanTime;
        this.banReason = banReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BanInfo banInfo = (BanInfo) o;

        if (banTime != null ? !banTime.equals(banInfo.banTime) : banInfo.banTime != null) return false;
        if (unbanTime != null ? !unbanTime.equals(banInfo.unbanTime) : banInfo.unbanTime != null) return false;
        return banReason != null ? banReason.equals(banInfo.banReason) : banInfo.banReason == null;
    }

    @Override
    public int hashCode() {
        int result = banTime != null ? banTime.hashCode() : 0;
        result = 31 * result + (unbanTime != null ? unbanTime.hashCode() : 0);
        result = 31 * result + (banReason != null ? banReason.hashCode() : 0);
        return result;
    }

    public Timestamp getBanTime() {
        return banTime;
    }

    public void setBanTime(Timestamp banTime) {
        this.banTime = banTime;
    }

    public Timestamp getUnbanTime() {
        return unbanTime;
    }

    public void setUnbanTime(Timestamp unbanTime) {
        this.unbanTime = unbanTime;
    }

    public BanReason getBanReason() {
        return banReason;
    }

    public void setBanReason(String banReason) {
        this.banReason.setReason(banReason);
    }
}
