package by.tr.web.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class BanInfo implements Serializable {
    private Timestamp banTime;
    private BanReason banReason;

    public BanInfo() {
        banReason = new BanReason();
    }

    public BanInfo(Timestamp banTime, BanReason banReason) {
        this.banTime = banTime;
        this.banReason = banReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BanInfo banInfo = (BanInfo) o;

        if (banTime != null ? !banTime.equals(banInfo.banTime) : banInfo.banTime != null) return false;
        return banReason != null ? banReason.equals(banInfo.banReason) : banInfo.banReason == null;
    }

    @Override
    public int hashCode() {
        int result = banTime != null ? banTime.hashCode() : 0;
        result = 31 * result + (banReason != null ? banReason.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BanInfo{" +
                "banTime=" + banTime +
                ", banReason=" + banReason +
                '}';
    }

    public Timestamp getBanTime() {
        return banTime;
    }

    public void setBanTime(Timestamp banTime) {
        this.banTime = banTime;
    }

    public BanReason getBanReason() {
        return banReason;
    }

    public void setBanReason(BanReason banReason) {
        this.banReason = banReason;
    }
}
