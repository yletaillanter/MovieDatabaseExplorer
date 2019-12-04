package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import androidx.annotation.NonNull;

import com.yoannlt.mde.moviedatabaseexplorer.BasePresenter;
import com.yoannlt.mde.moviedatabaseexplorer.BaseView;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;

/**
 * Created by yoannlt on 05/11/2016.
 */

public interface FavoriteContract {
    interface View extends BaseView<Presenter> {
        void showFavorites(@NonNull ArrayList<Movie> movies);
        void launchDetailMovie(@NonNull MovieComplete movieComplete);
    }

    interface Presenter extends BasePresenter {
        void getMovieComplete(int id);
    }
}
