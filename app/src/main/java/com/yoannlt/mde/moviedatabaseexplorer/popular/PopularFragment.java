package com.yoannlt.mde.moviedatabaseexplorer.popular;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ListSearchAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 24/10/2016.
 */

public class PopularFragment extends Fragment implements PopularContract.View, ClickListener {

    @BindView(R.id.popular_recyclerview) RecyclerView recyclerView;
    private PopularContract.Presenter presenter;
    private ListSearchAdapter adapter;


    public PopularFragment() {
    }

    public static PopularFragment newInstance() {
        return new PopularFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ListSearchAdapter(getActivity().getApplicationContext(), new ArrayList<Movie>());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(PopularContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_popular, container, false);
        //View root = super.onCreateView(inflater, container, savedInstanceState);

        ButterKnife.bind(this, root);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void showPopularMovies(ArrayList<Movie> movies) {
        Log.d("fragment", movies.toString());
        adapter.replace(movies);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        Toast.makeText(getActivity().getApplicationContext(), "id: "+adapter.getmDataset().get(position).getId(), Toast.LENGTH_SHORT).show();
    }
}
