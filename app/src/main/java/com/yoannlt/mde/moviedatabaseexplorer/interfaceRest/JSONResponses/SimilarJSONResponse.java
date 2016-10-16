package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses;

import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;

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
