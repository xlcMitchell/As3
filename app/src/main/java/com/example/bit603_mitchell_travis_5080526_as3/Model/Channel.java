package com.example.bit603_mitchell_travis_5080526_as3.Model;

public class Channel {
    private String channelId;
    private String channelTitle;

    private String channelDescription;
    private String channelSubscribers;

    public Channel() {
    }

    public Channel(String channelId, String channelTitle, String channelDescription,
                       String channelSubscribers) {
        this.channelId = channelId;
        this.channelTitle = channelTitle;
        this.channelDescription = channelDescription;
        this.channelSubscribers = channelSubscribers;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }


    public String getChannelDescription() {
        return channelDescription;
    }

    public void setChannelDescription(String channelDescription) {
        this.channelDescription = channelDescription;
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



    public String getChannelSubscribers() {
        return channelSubscribers;
    }
}

