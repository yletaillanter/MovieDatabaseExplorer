package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest;

import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.CastPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonImagesJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonCreditsJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.SimilarJSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yoannlt on 16/06/2016.
 */
public interface RequestInterface {

    // DEPRECATED
    @GET("search/movie/{movie}")
    Call<JSONResponse> getMovies(@Path("movie") String movie);

    @GET("movie/{id}")
    Call<MovieComplete> getMovieById(@Path("id") int id);

    @GET("movie/credit/{id}")
    Call<CastPersonJSONResponse> getMovieCredits(@Path("id") int id);

    @GET("movie/similar/{id}")
    Call<SimilarJSONResponse> getSimilarMovies(@Path("id") int id);

    @GET("person/{id}")
    Call<Person> getPerson(@Path("id") int id);

    @GET("person/credits/{id}")
    Call<PersonCreditsJSONResponse> getOtherMovies(@Path("id") int id);

    @GET("person/images/{id}")
    Call<PersonImagesJSONResponse> getPersonImages(@Path("id") int id);


    // API TMDB DIRECT
    @GET("search/movie")
    Call<JSONResponse> movieSearchTmdb(@Query("query") String query);

}