package com.yoannlt.mde.moviedatabaseexplorer.model;

/**
 * Created by yoannlt on 03/07/2016.
 */
public class OtherMoviesFromPerson {

    private boolean adult;
    private String character;
    private String credit_id;
    private String id;
    private String original_title;
    private String poster_path;
    private String release_date;
    private String title;

    public OtherMoviesFromPerson() {
    }

    public OtherMoviesFromPerson(boolean adult, String character, String credit_id, String id, String original_title, String poster_path, String release_date, String title) {
        this.adult = adult;
        this.character = character;
        this.credit_id = credit_id;
        this.id = id;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.title = title;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public void setCredit_id(String credit_id) {
        this.credit_id = credit_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
