package com.yoannlt.mde.moviedatabaseexplorer.fullList;

import android.support.annotation.NonNull;

import com.yoannlt.mde.moviedatabaseexplorer.BasePresenter;
import com.yoannlt.mde.moviedatabaseexplorer.BaseView;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yoannlt on 24/10/2016.
 */

public interface FullListContract {

    interface View extends BaseView<Presenter> {
        void showMovieList(@NonNull ArrayList<Movie> movies);
        void launchDetailMovie(@NonNull MovieComplete movie);
        void startAdvancedSearch();

    }

    interface Presenter extends BasePresenter {
        void loadPopular();
        void loadNowPlaying();
        void loadTopRated();
        void loadUpcoming();
        void initAdvancedSearch();
        void loadAdvancedSearch(@NonNull HashMap<String, String> queries);
        void getMovieComplete(@NonNull int id);
    }
}
