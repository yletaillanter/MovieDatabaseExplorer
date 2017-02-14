package com.yoannlt.mde.moviedatabaseexplorer.favorite;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ListSearchAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailActivity;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 05/11/2016.
 */

public class FavoriteFragment extends Fragment implements FavoriteContract.View, ClickListener {

    @BindView(R.id.favorite_recyclerview) RecyclerView recyclerView;
    private FavoriteContract.Presenter presenter;
    private ListSearchAdapter adapter;

    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, root);
        initRecyclerView();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(FavoriteContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        presenter.getMovieComplete(adapter.getmDataset().get(position).getId());
    }

    @Override
    public void showFavorites(ArrayList<Movie> movies){
        adapter.replace(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void launchDetailMovie(@NonNull MovieComplete movieComplete) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();
        Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
        intent.putExtra("movie", movieComplete);
        startActivity(intent, bundle);
    }

    private void initRecyclerView(){
        adapter = new ListSearchAdapter(getActivity().getApplicationContext(), new ArrayList<Movie>());
        adapter.setClickListener(this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
