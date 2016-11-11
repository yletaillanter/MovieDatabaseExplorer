package com.yoannlt.mde.moviedatabaseexplorer.module;

import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;

/**
 * Created by yoannlt on 11/11/2016.
 */

@Singleton
@Component(modules={HttpModule.class})
public interface MyModule {

    //OkHttpClient provideOkHttpClient();
    //RequestInterface provideRetrofit(OkHttpClient okHttpClient);

    void inject(MainActivity activity);
    // void inject(MyFragment fragment);
    // void inject(MyService service);
}
