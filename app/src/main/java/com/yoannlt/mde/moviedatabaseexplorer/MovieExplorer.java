package com.yoannlt.mde.moviedatabaseexplorer;

import android.app.Application;

import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by yoannlt on 11/11/2016.
 */

public class MovieExplorer extends Application {

    protected RequestInterface request;
    protected static Application application;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        application = this;
    }
}
