package com.example.bit603_mitchell_travis_5080526_as3.Model;

public class Channel {
    private String channelId;
    private String channelTitle;
    private String channelVideos;
    private String channelViews;
    private String channelSubscribers;

    public Channel() {
    }

    public Channel(String channelId, String channelTitle, String channelVideos,
                       String channelView, String channelSubscribers) {
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.channelVideos = channelVideos;
        this.channelViews = channelView;
        this.channelSubscribers = channelSubscribers;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public void setChannelVideos(String channelVideos) {
        this.channelVideos = channelVideos;
    }

    public void setChannelViews(String channelViews) {
        this.channelViews = channelViews;
    }

    public void setChannelSubscribers(String channelSubscribers) {
        this.channelSubscribers = channelSubscribers;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getChannelVideos() {
        return channelVideos;
    }

    public String getChannelViews() {
        return channelViews;
    }

    public String getChannelSubscribers() {
        return channelSubscribers;
    }
}

