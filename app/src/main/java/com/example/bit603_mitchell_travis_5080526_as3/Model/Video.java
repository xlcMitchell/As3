package com.example.bit603_mitchell_travis_5080526_as3.Model;

// Video.java
public class Video {
    public String title;
    public String videoId;
    public String thumbnailUrl;

    public Video() {} // Needed for Firestore
    public Video(String title, String videoId, String thumbnailUrl) {
        this.title = title;
        this.videoId = videoId;
        this.thumbnailUrl = thumbnailUrl;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}

