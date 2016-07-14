package com.yoannlt.mde.moviedatabaseexplorer.model;

/**
 * Created by yoannlt on 16/06/2016.
 */
public class JSONResponse {

    private Movie[] results;

    public Movie[] getResults() {
        return results;
    }

    public int count() {
        return results.length;
    }
}
