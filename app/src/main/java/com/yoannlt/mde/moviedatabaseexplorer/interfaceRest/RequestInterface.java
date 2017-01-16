package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest;

import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.CastPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONImagesResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonImagesJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.SearchPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.PersonCreditsJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.SimilarJSONResponse;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by yoannlt on 16/06/2016.
 */
public interface RequestInterface {


    // MOVIE
    @GET("3/search/movie")
    Observable<JSONResponse> movieSearchTmdb(@Query("query") String query);

    @GET("3/movie/{id}")
    Observable<MovieComplete> getMovieById(@Path("id") int id);

    @GET("3/movie/{id}/similar")
    Observable<SimilarJSONResponse> getSimilarMovies(@Path("id") int id);

    @GET("3/movie/{id}/credits")
    Observable<CastPersonJSONResponse> getMovieCredits(@Path("id") int id);

    @GET("3/discover/movie")
    Observable<JSONResponse> movieDiscoverTmdb(@QueryMap Map<String, String> queries);


    // PERSON
    @GET("3/person/{id}/images")
    Observable<PersonImagesJSONResponse> getPersonImage(@Path("id") int id);

    @GET("3/movie/{id}/images")
    Observable<JSONImagesResponse> getMovieImage(@Path("id") int id);

    @GET("3/person/{id}/movie_credits")
    Observable<PersonCreditsJSONResponse> getOtherMovies(@Path("id") int id);

    @GET("3/person/{id}")
    Observable<Person> getPerson(@Path("id") int id);

    @GET("3/search/person")
    Observable<SearchPersonJSONResponse> searchPerson(@Query("query") String query);


    //ACCEUIL
    @GET("3/movie/now_playing")
    Observable<JSONResponse> getNowPlaying();

    @GET("3/movie/upcoming")
    Observable<JSONResponse> getUpcoming();

    @GET("3/movie/popular")
    Observable<JSONResponse> getPopular();

    @GET("3/movie/top_rated")
    Observable<JSONResponse> getTopRated();

    //TODO: TV SHOW
}