package by.tr.web.domain;

import java.io.Serializable;

public class TvChannel implements Serializable {
    private static final long serialVersionUID = 4876774209996102703L;
    private String channelName;

    public TvChannel() {
    }
    public TvChannel(String channelName){
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TvChannel tvChannel = (TvChannel) o;

        return channelName.equals(tvChannel.channelName);
    }

    @Override
    public int hashCode() {
        return channelName.hashCode();
    }

    @Override
    public String toString() {
        return  channelName;
    }
}
