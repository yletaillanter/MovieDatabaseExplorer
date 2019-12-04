package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yoannlt on 05/11/2016.
 */

public class Favorite extends RealmObject {

    @PrimaryKey
    private int id;
    private int movieId;
    private String original_title;
    private String title;
    private String original_language;
    private String overview;
    private String poster_path;
    private String backdrop_path;
    private Double popularity;
    private Double vote_average;
    private String release_date;
    private boolean video;
    private boolean adult;

    public Favorite() {
    }

    public Favorite(int id, int movieId, String original_title, String title, String original_language, String overview, String poster_path, String backdrop_path, Double popularity, Double vote_average, String release_date, boolean video, boolean adult) {
        this.id = id;
        this.movieId = movieId;
        this.original_title = original_title;
        this.title = title;
        this.original_language = original_language;
        this.overview = overview;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.video = video;
        this.adult = adult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }


    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", original_title='" + original_title + '\'' +
                ", title='" + title + '\'' +
                ", original_language='" + original_language + '\'' +
                ", overview='" + overview + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", popularity=" + popularity +
                ", vote_average=" + vote_average +
                ", release_date='" + release_date + '\'' +
                ", video=" + video +
                ", adult=" + adult +
                '}';
    }
}
