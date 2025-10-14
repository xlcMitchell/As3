package com.example.bit603_mitchell_travis_5080526_as3;

import static org.junit.Assert.assertEquals;

import com.example.bit603_mitchell_travis_5080526_as3.Model.Video;

import org.junit.Test;

public class VideoTest {

    @Test
    public void testVideoGetters() {
        String title = "Test Title";
        String id = "abc123";
        String thumbnail = "https://example.com/thumb.jpg";
        Video video = new Video(title, id, thumbnail);
        assertEquals(title, video.getTitle());
        assertEquals(id, video.getVideoId());
        assertEquals(thumbnail, video.getThumbnailUrl());
    }

    @Test
    public void testVideoSetters() {
        Video video = new Video("", "", "");
        video.setTitle("New Title");
        video.setVideoId("newId");
        video.setThumbnailUrl("newThumb");
        assertEquals("New Title", video.getTitle());
        assertEquals("newId", video.getVideoId());
        assertEquals("newThumb", video.getThumbnailUrl());
    }
}
