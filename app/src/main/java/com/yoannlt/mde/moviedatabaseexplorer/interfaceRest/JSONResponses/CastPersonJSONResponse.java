package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses;

import com.yoannlt.mde.moviedatabaseexplorer.model.CastPerson;

/**
 * Created by yoannlt on 22/06/2016.
 */
public class CastPersonJSONResponse {

    private int id;
    private CastPerson[] cast;

    public CastPerson[] getCast() {
        return cast;
    }

    public int count() {
        return cast.length;
    }
}
