package me.API.Album;

import java.util.Objects;

public final class Art {
    private String url;
    private int height;
    private int width;

    public Art(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Art setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Art setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Art setWidth(int width) {
        this.width = width;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Art art = (Art) o;
        return url.equals(art.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }

    @Override
    public String toString() {
        return "Art{" +
                "url='" + url + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }
}
