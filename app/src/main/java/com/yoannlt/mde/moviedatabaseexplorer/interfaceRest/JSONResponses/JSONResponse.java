package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses;

import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;

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
