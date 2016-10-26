package com.yoannlt.mde.moviedatabaseexplorer.popular;

import android.support.annotation.NonNull;

import com.yoannlt.mde.moviedatabaseexplorer.BasePresenter;
import com.yoannlt.mde.moviedatabaseexplorer.BaseView;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;

/**
 * Created by yoannlt on 24/10/2016.
 */

public interface PopularContract {

    interface View extends BaseView<Presenter> {
        void showPopularMovies(@NonNull ArrayList<Movie> movies);
        void launchDetailMovie(@NonNull MovieComplete movie);

    }

    interface Presenter extends BasePresenter {
        void loadPopular();
        void getMovieComplete(@NonNull int id);
    }
}
