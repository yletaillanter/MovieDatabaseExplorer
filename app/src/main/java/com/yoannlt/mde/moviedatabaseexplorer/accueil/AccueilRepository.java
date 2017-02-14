package com.yoannlt.mde.moviedatabaseexplorer.accueil;

import android.util.Log;

import com.yoannlt.mde.moviedatabaseexplorer.MovieExplorer;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.JSONResponses.JSONResponse;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yoannlt on 02/02/2017.
 */

public class AccueilRepository {

    private final static String TAG = "AccueilRepository";
    private final static String POPULAR_SOURCE = "popular";

    @Inject RequestInterface request;
    private Realm realmUI;

    public AccueilRepository() {
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
    }

    public Observable<List<Movie>> loadPopular() {
        Log.d(TAG, "loadPopular");
        // Create the observable + write into db + read from db
        Observable<List<Movie> > observable = request.getPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(new Func1<JSONResponse, List<Movie>>() {
                    @Override
                    public List<Movie> call(JSONResponse jsonResponse) {
                        return popularToRealm(jsonResponse, POPULAR_SOURCE);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<List<Movie> , List<Movie> >() {
                    @Override
                    public List<Movie> call(List<Movie>  o) {
                        return findInRealm(POPULAR_SOURCE);
                    }
                });


        List<Movie> results = findInRealm(POPULAR_SOURCE);
        if (results.size() > 0)
            observable.mergeWith(Observable.just(results));

        realmUI.close();
        return observable;
    }

    private List<Movie> popularToRealm(final JSONResponse jsonResponse, String listSource) {
        ArrayList<Movie> movies =  new ArrayList<>(Arrays.asList(jsonResponse.getResults()));

        // TODO: Suppression de l'ancien cache (DEBUG)
        //emptyRealmCache();


        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        // Ajout des nouveaux films dans le cache
        // TODO: merger les données
        for (Movie movie : movies) {

            Movie movieUpdated = realm.where(Movie.class)
                    .equalTo("id", movie.getId())
                    .equalTo("listSource", listSource)
                    .findFirst();

            if (movieUpdated == null) {
                movie.setListSource(listSource);
                realm.insert(movie);
            } else {
                movieUpdated.setListSource(listSource);
                movieUpdated.setOverview(movie.getOverview());
                movieUpdated.setVote_average(movie.getVote_average());
                movieUpdated.setBackdrop_path(movie.getBackdrop_path());
                movieUpdated.setPoster_path(movie.getPoster_path());
                movieUpdated.setAdult(movie.isAdult());
                movieUpdated.setOriginal_language(movie.getOriginal_language());
                movieUpdated.setOriginal_title(movie.getOriginal_title());
                movieUpdated.setTitle(movie.getTitle());
                movieUpdated.setPopularity(movie.getPopularity());
                movieUpdated.setRelease_date(movie.getRelease_date());
                movieUpdated.setVideo(movie.isVideo());
            }

        }

        realm.commitTransaction();
        realm.close();

        return movies;
    }

    private RealmResults<Movie>  findInRealm( String listSource) {
        realmUI = Realm.getDefaultInstance();
        RealmResults<Movie> result = realmUI.where(Movie.class).equalTo("listSource", listSource).findAll();

        return result;
    }

    private void emptyRealmCache() {
        Log.d(TAG, "emptyRealmCache");
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Movie> rows = realm.where(Movie.class).equalTo("listSource",POPULAR_SOURCE).findAll();
                rows.deleteAllFromRealm();
            }
        });
    }
}
