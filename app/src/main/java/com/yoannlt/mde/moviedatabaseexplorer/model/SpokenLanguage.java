package com.yoannlt.mde.moviedatabaseexplorer.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yoannlt on 03/07/2016.
 */
public class SpokenLanguage implements Parcelable {

    private String iso_639_1;
    private String name;

    public SpokenLanguage() {
    }

    public SpokenLanguage(String iso_639_1, String name) {
        this.iso_639_1 = iso_639_1;
        this.name = name;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected SpokenLanguage(Parcel in) {
        iso_639_1 = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(iso_639_1);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SpokenLanguage> CREATOR = new Parcelable.Creator<SpokenLanguage>() {
        @Override
        public SpokenLanguage createFromParcel(Parcel in) {
            return new SpokenLanguage(in);
        }

        @Override
        public SpokenLanguage[] newArray(int size) {
            return new SpokenLanguage[size];
        }
    };
}