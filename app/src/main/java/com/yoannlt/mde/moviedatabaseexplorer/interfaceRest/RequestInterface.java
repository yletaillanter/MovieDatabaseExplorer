package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest;

import com.yoannlt.mde.moviedatabaseexplorer.model.CastPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.JSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;
import com.yoannlt.mde.moviedatabaseexplorer.model.PersonCreditsJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.SimilarJSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by yoannlt on 16/06/2016.
 */
public interface RequestInterface {

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
}