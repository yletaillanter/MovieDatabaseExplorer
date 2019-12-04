package com.yoannlt.mde.moviedatabaseexplorer.advancedSearch;

import android.content.Intent;
import androidx.fragment.app.DialogFragment;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ActorAdapter;
import com.yoannlt.mde.moviedatabaseexplorer.adapter.ClickListener;
import com.yoannlt.mde.moviedatabaseexplorer.model.Person;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yoannlt on 21/12/2016.
 */

public class MyActorsSearchDialogFragment extends DialogFragment implements ClickListener, AdvancedSearchContract.DialogView {

    private String LOG_TAG = getClass().getSimpleName();

    @BindView (R.id.search_input_actor) EditText searchInput;
    @BindView(R.id.actor_search_result_recycler_view) RecyclerView actorSearchRecyclerView;
    private ActorAdapter actorAdapter;
    private AdvancedSearchContract.Presenter presenter;

    public MyActorsSearchDialogFragment() {
    }

    public static MyActorsSearchDialogFragment newInstance() {
        return new MyActorsSearchDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actorAdapter = new ActorAdapter(new ArrayList<Person>());
        actorAdapter.setClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_actor_search, container, false);
        ButterKnife.bind(this, v);

        actorSearchRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        actorSearchRecyclerView.setLayoutManager(layoutManager);
        actorSearchRecyclerView.setAdapter(actorAdapter);

        searchInput.clearFocus();

        // add a listener when the search request is modified
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Start the searching when the search get over two characters
                if (searchInput.getText().toString().length()>1) {
                    Log.d(LOG_TAG, "searchInput: "+searchInput.getText().toString());
                    presenter.searchActor(searchInput.getText().toString());
                    //searchMovie(searchInput.getText().toString());
                } else {
                    // Else empty the result list (when deleting the search input)
                    actorAdapter.replace(new ArrayList<Person>());
                    actorAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    //TODO : close and add the actor to the list
    @Override
    public void itemClicked(View view, int position, String recycler) {
        Intent i = new Intent(getActivity(), AdvancedSearchActivity.class);
        i.putExtra("person", actorAdapter.getPersons().get(position));
        getTargetFragment().onActivityResult(getTargetRequestCode(), 1, i);
        this.dismiss();
    }

    @Override
    public void showResults(ArrayList<Person> persons) {
        actorAdapter.replace(persons);
        actorAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(AdvancedSearchContract.Presenter presenter) {
        this.presenter = presenter;
        this.presenter.setDialogView(this);
    }
}
