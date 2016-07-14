package com.yoannlt.mde.moviedatabaseexplorer.model;

/**
 * Created by yoannlt on 21/06/2016.
 */
public class SimilarJSONResponse {
    private Movie[] results;

    public Movie[] getResults() {
        return results;
    }

    public int count() {
        return results.length;
    }
}
