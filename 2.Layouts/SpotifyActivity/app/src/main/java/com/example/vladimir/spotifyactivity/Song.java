package com.example.vladimir.spotifyactivity;

/**
 * Created by Vladimir on 05-Sep-16.
 */
public class Song {
    private String Name;
    private String Author;
    private String Album;

    public Song(String name, String author, String album) {
        Name = name;
        Author = author;
        Album = album;
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
