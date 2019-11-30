package com.yoannlt.mde.moviedatabaseexplorer.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 23/12/2016.
 */

public class ActorSearchListAdapter extends RecyclerView.Adapter<ActorSearchListAdapter.ViewHolder> {

    /* List of data */
    private ArrayList<Person> persons = new ArrayList<Person>();
    private Context context;
    private ClickListener clickListener;

    private Person person;

    public ActorSearchListAdapter(Context context, ArrayList<Person> persons) {
        this.context = context;
        this.persons = persons;
    }

    @Override
    public ActorSearchListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_search_actor_list, parent, false);
        return new ActorSearchListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Getting the movie
        person = persons.get(position);

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

        @BindView(R.id.person_name_list) TextView name ;
        @BindView(R.id.person_delete) ImageView delete;


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
        return this.persons;
    }
}
