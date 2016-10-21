package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses;

import com.yoannlt.mde.moviedatabaseexplorer.model.Image;

/**
 * Created by yoannlt on 18/10/2016.
 */

public class JSONImagesResponse {
    private int id;
    private Image[] backdrops;
    private Image[] posters;

    public JSONImagesResponse() {
    }

    public JSONImagesResponse(int id, Image[] backdrops, Image[] posters) {
        this.id = id;
        this.backdrops = backdrops;
        this.posters = posters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Image[] getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(Image[] backdrops) {
        this.backdrops = backdrops;
    }

    public Image[] getPosters() {
        return posters;
    }

    public void setPosters(Image[] posters) {
        this.posters = posters;
    }
}
