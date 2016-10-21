package com.yoannlt.mde.moviedatabaseexplorer.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoannlt on 22/06/2016.
 */
public class Person implements Parcelable {

    private int id;
    private String imdb_id;
    private String name;
    private List<String> also_known_as;
    private String biography;
    private String birthday;
    private String deathday;
    private String place_of_birth;
    private double popularity;
    private String profile_path;
    private String homepage;
    private boolean adult;

    public Person() {
    }

    public Person(int id, String imdb_id, String name, List<String> also_known_as, String biography, String birthday, String deathday, String place_of_birth, double popularity, String profile_path, String homepage, boolean adult) {
        this.id = id;
        this.imdb_id = imdb_id;
        this.name = name;
        this.also_known_as = also_known_as;
        this.biography = biography;
        this.birthday = birthday;
        this.deathday = deathday;
        this.place_of_birth = place_of_birth;
        this.popularity = popularity;
        this.profile_path = profile_path;
        this.homepage = homepage;
        this.adult = adult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAlso_known_as() {
        return also_known_as;
    }

    public void setAlso_known_as(List<String> also_known_as) {
        this.also_known_as = also_known_as;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    protected Person(Parcel in) {
        id = in.readInt();
        imdb_id = in.readString();
        name = in.readString();
        if (in.readByte() == 0x01) {
            also_known_as = new ArrayList<String>();
            in.readList(also_known_as, String.class.getClassLoader());
        } else {
            also_known_as = null;
        }
        biography = in.readString();
        birthday = in.readString();
        deathday = in.readString();
        place_of_birth = in.readString();
        popularity = in.readDouble();
        profile_path = in.readString();
        homepage = in.readString();
        adult = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(imdb_id);
        dest.writeString(name);
        if (also_known_as == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(also_known_as);
        }
        dest.writeString(biography);
        dest.writeString(birthday);
        dest.writeString(deathday);
        dest.writeString(place_of_birth);
        dest.writeDouble(popularity);
        dest.writeString(profile_path);
        dest.writeString(homepage);
        dest.writeByte((byte) (adult ? 0x01 : 0x00));
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", imdb_id='" + imdb_id + '\'' +
                ", name='" + name + '\'' +
                ", also_known_as=" + also_known_as +
                ", biography='" + biography + '\'' +
                ", birthday='" + birthday + '\'' +
                ", deathday='" + deathday + '\'' +
                ", place_of_birth='" + place_of_birth + '\'' +
                ", popularity=" + popularity +
                ", profile_path='" + profile_path + '\'' +
                ", homepage='" + homepage + '\'' +
                ", adult=" + adult +
                '}';
    }
}