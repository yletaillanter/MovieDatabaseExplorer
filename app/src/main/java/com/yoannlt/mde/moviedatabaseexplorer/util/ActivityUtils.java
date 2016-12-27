package com.yoannlt.mde.moviedatabaseexplorer.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

/**
 * Created by yoannlt on 25/10/2016.
 */

public class ActivityUtils {

    public static final String FROM = "from";
    public static final String FROM_RECYCLER_NOW_PLAYING = "now_playing";
    public static final String FROM_RECYCLER_UP_COMING = "up_coming";
    public static final String FROM_RECYCLER_POPULAR = "popular";
    public static final String FROM_RECYCLER_TOP_RATED = "top_rated";
    public static final String FROM_ADVANCED_SEARCH = "advanced_search";

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static void replaceFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                                 @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }

    public static void removeFragment(@NonNull FragmentManager fragmentManager,
                                      @NonNull Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    public static Movie movieCompleteToMovie(@NonNull MovieComplete movieComplete) {
        Movie movie = new Movie();
        movie.setId(movieComplete.getId());
        movie.setTitle(movieComplete.getTitle());
        movie.setPoster_path(movieComplete.getPoster_path());
        movie.setBackdrop_path(movieComplete.getBackdrop_path());
        movie.setVote_average(movieComplete.getVote_average());

        Log.d("Utils: overview:", movieComplete.getOverview());
        movie.setOverview(movieComplete.getOverview());
        Log.d("Utils: overview:", movie.toString());
        return movie;
    }
}
