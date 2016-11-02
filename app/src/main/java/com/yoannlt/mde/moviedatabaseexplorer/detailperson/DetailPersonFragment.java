package com.yoannlt.mde.moviedatabaseexplorer.detailperson;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.OtherMoviesAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailActivity;
import com.yoannlt.mde.moviedatabaseexplorer.fullscreen.FullScreenImageViewActivity;
import com.yoannlt.mde.moviedatabaseexplorer.gallery.GalleryActivity;
import com.yoannlt.mde.moviedatabaseexplorer.model.MovieComplete;
import com.yoannlt.mde.moviedatabaseexplorer.model.OtherMoviesFromPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 26/10/2016.
 */

public class DetailPersonFragment  extends Fragment implements DetailPersonContract.View, ClickListener {

    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";

    @BindView(R.id.collapse_toolbar_person) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.picture_person) ImageView picture;
    @BindView(R.id.birthdate) TextView birtdate;
    @BindView(R.id.birthplace) TextView birtplace;
    @BindView(R.id.biography) TextView biograhy;
    @BindView(R.id.recycler_other) RecyclerView recyclerViewOther;
    @BindView(R.id.gallery) ImageView gallery;


    private ArrayList<OtherMoviesFromPerson> otherMovies;
    private OtherMoviesAdapter adapter;

    private MovieComplete movie;
    private Person person;

    private DetailPersonContract.Presenter presenter;

    public DetailPersonFragment() {
    }

    public static DetailPersonFragment newInstance() {
        return new DetailPersonFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        person = getActivity().getIntent().getParcelableExtra("person");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail_person, container, false);
        ButterKnife.bind(this, root);


        //final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.my_toolbar_detail);
        //getActivity().setActionBar(toolbar);
        //getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbarLayout.setTitle(person.getName());

        Picasso.with(getActivity().getApplicationContext()).load(BASE_IMAGE_URL + person.getProfile_path()).fit().into(picture);
        birtdate.setText(person.getBirthday());
        birtplace.setText(person.getPlace_of_birth());
        biograhy.setText(person.getBiography());

        //Init du recyclerView other
        otherMovies = new ArrayList<OtherMoviesFromPerson>();
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewOther.setLayoutManager(horizontalLayoutManager);
        adapter = new OtherMoviesAdapter(getActivity().getApplicationContext(), otherMovies);
        adapter.setClickListener(this);
        recyclerViewOther.setAdapter(adapter);
        presenter.loadOtherMovies(person.getId());

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity().getApplicationContext(), GalleryActivity.class);
                i.putExtra("from", "person");
                i.putExtra("person", person);
                startActivity(i);
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), FullScreenImageViewActivity.class);
                i.putExtra("img", person.getProfile_path());
                startActivity(i);
            }
        });

        return root;
    }

    @Override
    public void setPresenter(DetailPersonContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setOtherMovies(@NonNull ArrayList<OtherMoviesFromPerson> movies) {
        this.otherMovies = movies;
        adapter.replace(movies);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setMovieComplete(@NonNull MovieComplete movie) {
        this.movie = movie;
    }

    @Override
    public void showDetailMovie(@NonNull MovieComplete movie) {
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra("movie", movie);
        startActivity(i);
    }

    @Override
    public void itemClicked(View view, int position, String recycler) {
        presenter.loadMovieComplete(Integer.parseInt(otherMovies.get(position).getId()));
    }
}
