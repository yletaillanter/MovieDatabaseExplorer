package com.yoannlt.mde.moviedatabaseexplorer.popular;

import android.support.annotation.NonNull;

import com.yoannlt.mde.moviedatabaseexplorer.BasePresenter;
import com.yoannlt.mde.moviedatabaseexplorer.BaseView;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoannlt on 24/10/2016.
 */

public interface PopularContract {

    interface View extends BaseView<Presenter> {
        void showPopularMovies(ArrayList<Movie> movies);

    }

    interface Presenter extends BasePresenter {
        void loadPopular();
        void openMovieDetail(@NonNull MovieComplete movieComplete);
    }
}
