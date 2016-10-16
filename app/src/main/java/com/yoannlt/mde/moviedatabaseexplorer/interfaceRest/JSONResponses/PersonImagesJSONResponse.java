package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses;

import com.yoannlt.mde.moviedatabaseexplorer.model.Profiles;

/**
 * Created by yoannlt on 10/09/2016.
 */
public class PersonImagesJSONResponse  {

    private int id;
    private Profiles[] profiles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Profiles[] getProfiles() {
        return profiles;
    }

    public void setProfiles(Profiles[] profiles) {
        this.profiles = profiles;
    }

    public int count() {
        return profiles.length;
    }
}
