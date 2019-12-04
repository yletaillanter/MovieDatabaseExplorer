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

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import io.realm.Realm;
import io.realm.RealmResults;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by yoannlt on 02/02/2017.
 */
public class AccueilRepository {

    private final String LOG_TAG = getClass().getSimpleName();
    private final static String POPULAR_SOURCE = "popular";

    @Inject RequestInterface request;
    private Realm realmUI;

    protected AccueilRepository() {
        MovieExplorer.application().getMovieExplorerComponent().inject(this);
    }

    protected Flowable<List<Movie>> loadPopular() {
        Log.d(LOG_TAG, "loadPopular");
        // Create the observable + write into db + read from db
        Flowable<List<Movie> > observable = request.getPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map(new Function<JSONResponse, List<Movie>>() {
                    @Override
                    public List<Movie> apply(JSONResponse jsonResponse) {
                        return popularToRealm(jsonResponse, POPULAR_SOURCE);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<Movie> , List<Movie> >() {
                    @Override
                    public List<Movie> apply(List<Movie>  o) {
                        return findInRealm(POPULAR_SOURCE);
                    }
                });


        List<Movie> results = findInRealm(POPULAR_SOURCE);
        if (results.size() > 0)
            observable.mergeWith(Flowable.just(results));

        realmUI.close();
        return observable;
    }

    private List<Movie> popularToRealm(final JSONResponse jsonResponse, String listSource) {
        Log.d(LOG_TAG, "popularToRealm");

        ArrayList<Movie> movies =  new ArrayList<>(Arrays.asList(jsonResponse.getResults()));

        // TODO: Suppression de l'ancien cache (DEBUG)
        //emptyRealmCache();

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        // Ajout des nouveaux films dans le cache
        // TODO: merger les données
        for (Movie movie : movies) {
            Log.d(LOG_TAG, "Ajout dans le cache: " + movies.toString());

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
        Log.d(LOG_TAG, "findInRealm");

        realmUI = Realm.getDefaultInstance();
        return realmUI.where(Movie.class).equalTo("listSource", listSource).findAll();
    }

    private void emptyRealmCache() {
        Log.d(LOG_TAG, "emptyRealmCache");

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
