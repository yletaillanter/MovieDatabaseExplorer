package com.yoannlt.mde.moviedatabaseexplorer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.model.CastPerson;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 21/12/2016.
 */

public class ActorAdapter extends RecyclerView.Adapter<ActorAdapter.ViewHolder> {

    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w300";

    /* List of data */
    private ArrayList<Person> persons = new ArrayList<Person>();
    private Context context;
    private ClickListener clickListener;
    private Person person;

    public ActorAdapter(Context context, ArrayList<Person> persons) {
        this.context = context;
        this.persons = persons;
    }

    //TODO : create & change layout adapter
    @Override
    public ActorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_search_actor, parent, false);
        return new ActorAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ActorAdapter.ViewHolder holder, int position) {
        // Getting the movie
        person = persons.get(position);

        // Setting the picture
        if (person.getProfile_path() != null) {
            Picasso.with(context).load(BASE_IMAGE_URL + person.getProfile_path()).into(holder.picture);
        }
        // Setting the name
        if (!person.getName().equals("")) {
            holder.name.setText(person.getName());
        }
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.person_picture_search) ImageView picture;
        @BindView(R.id.person_name_search) TextView name ;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);

            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if (clickListener != null) {
                clickListener.itemClicked(v,getPosition(),CastingRecyclerAdapter.class.getSimpleName());
            }
        }
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /* Add an item to the dataList */
    public void add(int position, Person person) {
        persons.add(position, person);
        notifyItemInserted(position);
    }

    /* Remove an item to the dataList */
    public void remove(int id) {
        int position = persons.indexOf(id);
        persons.remove(position);
        notifyItemRemoved(position);
    }

    // Replace the data list
    public void replace(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }
}
