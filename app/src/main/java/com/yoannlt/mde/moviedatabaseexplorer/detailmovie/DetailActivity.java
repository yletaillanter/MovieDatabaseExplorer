package com.yoannlt.mde.moviedatabaseexplorer.detailmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.transition.TransitionInflater;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import butterknife.ButterKnife;


public class DetailActivity extends AppCompatActivity {

    private DetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpAnimation();
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        DetailFragment fragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameDetail);
        if (fragment == null) {
            fragment = DetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrameDetail);
        }

        presenter = new DetailPresenter(fragment);
        fragment.setPresenter(presenter);

    }

    public void setUpAnimation(){
        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.exit);
        getWindow().setExitTransition(slide);
    }
}