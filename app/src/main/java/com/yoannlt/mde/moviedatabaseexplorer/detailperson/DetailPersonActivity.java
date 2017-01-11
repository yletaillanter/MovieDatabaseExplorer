package com.yoannlt.mde.moviedatabaseexplorer.detailperson;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.yoannlt.mde.moviedatabaseexplorer.R;
import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.fullList.FullListActivity;
import com.yoannlt.mde.moviedatabaseexplorer.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPersonActivity extends AppCompatActivity {

    private DetailPersonPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_person);

        DetailPersonFragment fragment = (DetailPersonFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrameDetailPerson);
        if (fragment == null) {
            fragment = DetailPersonFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrameDetailPerson);
        }

        presenter = new DetailPersonPresenter(fragment);
        fragment.setPresenter(presenter);
    }
}
