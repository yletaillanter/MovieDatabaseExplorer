package com.yoannlt.mde.moviedatabaseexplorer.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.model.OtherMoviesFromPerson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 03/07/2016.
 */
public class OtherMoviesAdapter extends RecyclerView.Adapter<OtherMoviesAdapter.ViewHolder>{

    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";

    /* List of data */
    private ArrayList<OtherMoviesFromPerson> mDataset = new ArrayList<OtherMoviesFromPerson>();
    private Context context;
    private ClickListener clickListener;
    private OtherMoviesFromPerson movie;

    /* Constructor */
    public OtherMoviesAdapter(Context context, ArrayList<OtherMoviesFromPerson> movies) {
        this.context = context;
        this.mDataset = movies;
    }

    @Override
    public OtherMoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_horizontal_similar, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the movie
        movie = mDataset.get(position);

        if (movie.getPoster_path() != null) {
            Picasso.get().load(BASE_IMAGE_URL + movie.getPoster_path()).fit().into(holder.poster);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.similar_poster)
        ImageView poster;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v,getPosition(), HorizontalRecyclerAdapter.class.getSimpleName());
            }
        }
    }

    // Set the click listener
    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    // Replace the data list
    public void replace(ArrayList<OtherMoviesFromPerson> newMovieList) {
        this.mDataset = newMovieList;
    }

    // Get the data list
    public ArrayList<OtherMoviesFromPerson> getmDataset() {
        return mDataset;
    }
}
