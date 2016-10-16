package com.yoannlt.mde.moviedatabaseexplorer.model;

/**
 * Created by yoannlt on 10/09/2016.
 */
public class Profiles {

    private float aspect_ratio;
    private String file_path;
    private int height;
    private float vote_average;
    private int vote_count;
    private int width;

    public Profiles() {
    }

    public Profiles(float aspect_ratio, String file_path, int height, float vote_average, int vote_count, int width) {
        this.aspect_ratio = aspect_ratio;
        this.file_path = file_path;
        this.height = height;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.width = width;
    }

    public float getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(float aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
