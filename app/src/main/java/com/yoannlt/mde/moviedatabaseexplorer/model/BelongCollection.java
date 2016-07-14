package com.yoannlt.mde.moviedatabaseexplorer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yoannlt on 03/07/2016.
 */
public class BelongCollection implements Parcelable {

    private int id;
    private String name;
    private String poster_path;
    private String backdrop_path;

    public BelongCollection() {
    }

    public BelongCollection(int id, String name, String poster_path, String backdrop_path) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    protected BelongCollection(Parcel in) {
        id = in.readInt();
        name = in.readString();
        poster_path = in.readString();
        backdrop_path = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(poster_path);
        dest.writeString(backdrop_path);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<BelongCollection> CREATOR = new Parcelable.Creator<BelongCollection>() {
        @Override
        public BelongCollection createFromParcel(Parcel in) {
            return new BelongCollection(in);
        }

        @Override
        public BelongCollection[] newArray(int size) {
            return new BelongCollection[size];
        }
    };
}
