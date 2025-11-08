package me.API.Album;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Track implements Serializable, Comparable<Track> {
    @Serial
    private static final long serialVersionUID = 3_7_4L;

    private String author;
    private String title;
    private List<Art> albumArt;
    private String id;
    private int popularity;
    private int duration;
    private boolean isExplicit;

    public Track() {}

    public boolean isExplicit() {
        return isExplicit;
    }

    public Track setExplicit(boolean explicit) {
        isExplicit = explicit;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public Track setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public int getPopularity() {
        return popularity;
    }

    public Track setPopularity(int popularity) {
        this.popularity = popularity;
        return this;
    }

    public String getId() {
        return id;
    }

    public Track setId(String id) {
        this.id = id;
        return this;
    }

    public List<Art> getAlbumArt() {
        return albumArt;
    }

    public Track setAlbumArt(List<Art> albumArt) {
        this.albumArt = albumArt;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Track setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Track setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        Track track = (Track) o;

        return author.equals(track.author) && title.equals(track.title) && id.equals(track.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, id);
    }

    @Override
    public String toString() {
        return "Track{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", albumArt=" + albumArt +
                ", id='" + id + '\'' +
                ", popularity=" + popularity +
                ", duration=" + duration +
                ", isExplicit=" + isExplicit +
                '}';
    }

    @Override
    public int compareTo(Track o) {
        return id.compareTo(o.getId());
    }
}