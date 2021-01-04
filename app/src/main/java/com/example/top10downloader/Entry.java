package com.example.top10downloader;

public class Entry {
    private String name;
    private String artist;
    private String releaseDate;
    private String Summary;
    private String imageURL;

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getSummary() {
        return Summary;
    }

    public String getImageURL() {
        return imageURL;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    @Override
    public String toString() {
        return  "name='" + name + '\n' +
                "artist='" + artist + '\n' +
                "releaseDate='" + releaseDate + '\n' +
                "imageURL='" + imageURL + '\n';
    }

}
