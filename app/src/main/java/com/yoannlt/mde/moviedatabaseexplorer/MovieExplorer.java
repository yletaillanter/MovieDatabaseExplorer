package com.yoannlt.mde.moviedatabaseexplorer;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.yoannlt.mde.moviedatabaseexplorer.accueil.AccueilPresenter;
import com.yoannlt.mde.moviedatabaseexplorer.activity.MainActivity;
import com.yoannlt.mde.moviedatabaseexplorer.advancedSearch.AdvancedSearchPresenter;
import com.yoannlt.mde.moviedatabaseexplorer.detailmovie.DetailPresenter;
import com.yoannlt.mde.moviedatabaseexplorer.detailperson.DetailPersonPresenter;
import com.yoannlt.mde.moviedatabaseexplorer.favorite.FavoritePresenter;
import com.yoannlt.mde.moviedatabaseexplorer.fullList.FullListPresenter;
import com.yoannlt.mde.moviedatabaseexplorer.gallery.GalleryActivity;
import com.yoannlt.mde.moviedatabaseexplorer.interfaceRest.RequestInterface;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by yoannlt on 11/11/2016.
 */

public class MovieExplorer extends Application {

    protected static MovieExplorer application;
    private MovieExplorerComponent movieExplorerComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfiguration);

        application = this;
        movieExplorerComponent = DaggerMovieExplorer_MovieExplorerComponent.create();
    }

    public MovieExplorerComponent getMovieExplorerComponent() {
        return movieExplorerComponent;
    }

    public static MovieExplorer application() {
        return application;
    }


    @Component(modules = MovieExplorerModule.class)
    @Singleton
    public interface MovieExplorerComponent {
        void inject(MainActivity activity);
        void inject(DetailPresenter presenter);
        void inject(DetailPersonPresenter presenter);
        void inject(FavoritePresenter presenter);
        void inject(GalleryActivity activity);
        void inject(FullListPresenter presenter);
        void inject(AccueilPresenter presenter);
        void inject(AdvancedSearchPresenter presenter);

    }

    @Module
    public static class MovieExplorerModule {

        private final String API_KEY_PARAM = "api_key";
        private final String API_KEY = "a1c65ce9d24b2d4ed117f413bb94a122";
        private final String BASE_URL_TMDB = "https://api.themoviedb.org/";

        @Provides
        @Singleton
        public SharedPreferences providesSharedPreferences(Application application) {
            return PreferenceManager.getDefaultSharedPreferences(application);
        }


        @Provides
        @Singleton
        public Interceptor provideInterceptor() {

            return new Interceptor() {
                @Override
                public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAM, API_KEY).build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }
            };

        }

        @Provides
        @Singleton
        public OkHttpClient provideOkHttp(Interceptor interceptor) {

            /* FOR DEBUG
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            */

            return new OkHttpClient.Builder().addInterceptor(interceptor).build();
        }

        @Provides
        @Singleton
        public GsonConverterFactory provideGsonConverter() {
            return GsonConverterFactory.create();
        }

        @Provides
        @Singleton
        public Retrofit provideRetrofit(OkHttpClient okHttpClient,GsonConverterFactory gsonConverterFactory) {
            return new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_URL_TMDB)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(gsonConverterFactory)
                    .build();
        }
        @Provides
        @Singleton
        public RequestInterface provideRequestInterface(Retrofit retrofit) {
            return retrofit.create(RequestInterface.class);
        }
    }
}
