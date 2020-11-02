package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses;

import com.yoannlt.mde.moviedatabaseexplorer.model.CastPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.CrewPerson;

/**
 * Created by yoannlt on 22/06/2016.
 */
public class CastPersonJSONResponse {

    private int id;
    private CastPerson[] cast;
    private CrewPerson[] crew;

    public CastPerson[] getCast() {
        return cast;
    }

    public CrewPerson[] getCrew() {
        return crew;
    }
}
