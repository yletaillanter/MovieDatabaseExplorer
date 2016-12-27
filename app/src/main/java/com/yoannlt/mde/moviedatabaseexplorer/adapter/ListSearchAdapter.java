package com.yoannlt.mde.moviedatabaseexplorer.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 * Created by yoannlt on 16/06/2016.
 */
public class ListSearchAdapter extends RecyclerView.Adapter<ListSearchAdapter.ViewHolder>{

    /* List of data */
    private ArrayList<Movie> mDataset = new ArrayList<Movie>();

    private ClickListener clickListener;
    private Context context;
    private Movie movie;
    private ViewHolder holderRef;

    private final int MIN_SIZE_OVERVIEW = 0;
    private final int MAX_SIZE_OVERVIEW = 150;
    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";

    /* Constructor */
    public ListSearchAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.mDataset = movies;
    }

    @Override
    public ListSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the movie
        movie = mDataset.get(position);
        this.holderRef = holder;

        Resources res = context.getResources();
        boolean is_720 = res.getBoolean(R.bool.is_720);

        if (movie.getPoster_path() != null) {
            Picasso.with(context).load(BASE_IMAGE_URL + movie.getPoster_path()).fit().into(holder.poster);
        }

        if (!is_720 && mDataset.get(position).getOverview().length() > MAX_SIZE_OVERVIEW) {
            // Set the Title of the movie
            holder.title.setText(movie.getTitle() + " " + movie.getVote_average().toString());

            //Get the first 200 Character of the Overview
            String OverviewCutted = movie.getOverview().substring(MIN_SIZE_OVERVIEW, MAX_SIZE_OVERVIEW);

            // Set the overview to the view
            holder.overview.setText(OverviewCutted + "...");
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mDataset != null)
            return mDataset.size();
        else
            return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.card) CardView card;
        @Nullable @BindView(R.id.title) TextView title;
        @Nullable @BindView(R.id.overview) TextView overview;

        @BindView(R.id.poster) ImageView poster;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v,getPosition(), ListSearchAdapter.class.getSimpleName());
            }
        }

        public ImageView getPoster() {
            return poster;
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /* Add an item to the dataList */
    public void add(int position, Movie movie) {
        mDataset.add(position, movie);
        notifyItemInserted(position);
    }

    /* Remove an item to the dataList */
    public void remove(int id) {
        int position = mDataset.indexOf(id);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    // Replace the data list
    public void replace(ArrayList<Movie> newMovieList) {
        this.mDataset = newMovieList;
    }

    public ArrayList<Movie> getmDataset() {
        return mDataset;
    }

    public ViewHolder getViewHolderInstance(){
        return holderRef;
    }
}
