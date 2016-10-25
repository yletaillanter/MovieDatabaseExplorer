package com.yoannlt.mde.moviedatabaseexplorer.interfaceRest;

import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.CastPersonJSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONImagesResponse;
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

    @GET("search/movie")
    Observable<JSONResponse> movieSearchTmdb(@Query("query") String query);

    @GET("movie/popular")
    Observable<JSONResponse> getPopular();

    @GET("movie/{id}")
    Observable<MovieComplete> getMovieById(@Path("id") int id);

    @GET("movie/{id}/similar")
    Observable<SimilarJSONResponse> getSimilarMovies(@Path("id") int id);

    @GET("person/{id}")
    Observable<Person> getPerson(@Path("id") int id);

    @GET("movie/{id}/credits")
    Observable<CastPersonJSONResponse> getMovieCredits(@Path("id") int id);

    @GET("person/{id}/movie_credits")
    Observable<PersonCreditsJSONResponse> getOtherMovies(@Path("id") int id);

    @GET("3/movie/{id}/images")
    Observable<JSONImagesResponse> getMovieImage(@Path("id") int id);

    @GET("3/person/{id}/images")
    Observable<PersonImagesJSONResponse> getPersonImage(@Path("id") int id);

}