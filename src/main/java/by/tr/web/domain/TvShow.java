package by.tr.web.domain;

import java.io.Serializable;

public class TvShow extends Show implements Serializable {

    private static final long serialVersionUID = -5308976621430127666L;

    private ShowStatus showStatus;
    private int seasonsNum;
    private int episodesNum;
    private int finishedYear;
    private TvChannel channel;

    public enum ShowStatus {
        FINISHED, RETURNING, PAUSE
    }
    public enum  TvShowOrderType {
        TITLE, YEAR, RATING, SEASONS
    }
    public TvShow() {
        showStatus = ShowStatus.RETURNING;
        channel = new TvChannel();
    }

    public ShowStatus getShowStatus() {
        return showStatus;
    }
    public String getFormattedShowStatus(){
        return showStatus.toString().toLowerCase();
    }
    public void setShowStatus(ShowStatus showStatus) {
        this.showStatus = showStatus;
    }


    public void setShowStatus(String status) {
        this.showStatus = ShowStatus.valueOf(status.toUpperCase());
    }

    public int getSeasonsNum() {
        return seasonsNum;
    }

    public void setSeasonsNum(int seasonsNum) {
        this.seasonsNum = seasonsNum;
    }

    public int getEpisodesNum() {
        return episodesNum;
    }

    public void setEpisodesNum(int episodesNum) {
        this.episodesNum = episodesNum;
    }

    public int getFinishedYear() {
        return finishedYear;
    }

    public void setFinishedYear(int finishedYear) {
        this.finishedYear = finishedYear;
    }

    public TvChannel getChannel() {
        return channel;
    }

    public void setChannel(TvChannel channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TvShow tvShow = (TvShow) o;

        if (seasonsNum != tvShow.seasonsNum) return false;
        if (episodesNum != tvShow.episodesNum) return false;
        if (finishedYear != tvShow.finishedYear) return false;
        if (showStatus != tvShow.showStatus) return false;
        return channel.equals(tvShow.channel);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + showStatus.hashCode();
        result = 31 * result + seasonsNum;
        result = 31 * result + episodesNum;
        result = 31 * result + finishedYear;
        result = 31 * result + channel.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TVShow{" +
                "showStatus=" + showStatus +
                ", seasonsNum=" + seasonsNum +
                ", episodesNum=" + episodesNum +
                ", finishedYear=" + finishedYear +
                ", channel=" + channel +
                "} " + super.toString();
    }
}
