package com.example.demoappmusic7;

public class Song {
    private String title;
    private String artist;
    private int file;
    private int cover;

    public Song(String title, String artist, int file, int cover) {
        this.title = title;
        this.artist = artist;
        this.file = file;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        this.file = file;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }
}
