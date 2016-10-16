package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses;

import com.yoannlt.mde.moviedatabaseexplorer.model.OtherMoviesFromPerson;

/**
 * Created by yoannlt on 03/07/2016.
 */
public class PersonCreditsJSONResponse {

    private OtherMoviesFromPerson[] cast;

    public OtherMoviesFromPerson[] getCast() {
        return cast;
    }

    public int count() {
        return cast.length;
    }
}
