package com.example.vladimir.spotifyactivity;

/**
 * Created by Vladimir on 05-Sep-16.
 */
public class Song {
    private String Name;
    private String Author;
    private String Album;
    private Integer Song;

    public Song(String name, String author, String album, Integer song) {
        Name = name;
        Author = author;
        Album = album;
        Song = song;
    }

    public Integer getSong() {
        return Song;
    }

    public String getName() {
        return Name;
    }

    public String getAuthor() {
        return Author;
    }

    public String getAlbum() {
        return Album;
    }
}
