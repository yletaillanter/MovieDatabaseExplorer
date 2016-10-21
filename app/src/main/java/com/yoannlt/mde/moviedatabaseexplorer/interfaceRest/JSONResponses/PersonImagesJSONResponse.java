package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses;

import com.yoannlt.mde.moviedatabaseexplorer.model.Image;

import java.util.Arrays;

/**
 * Created by yoannlt on 10/09/2016.
 */
public class PersonImagesJSONResponse  {

    private int id;
    private Image[] profiles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image[] getProfiles() {
        return profiles;
    }

    public void setProfiles(Image[] profiles) {
        this.profiles = profiles;
    }

    public int count() {
        return profiles.length;
    }

    @Override
    public String toString() {
        return "PersonImagesJSONResponse{" +
                "id=" + id +
                ", profiles=" + Arrays.toString(profiles) +
                '}';
    }
}
