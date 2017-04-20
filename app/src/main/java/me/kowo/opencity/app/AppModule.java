package me.kowo.opencity.app;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.kowo.opencity.interfaces.CityGuideApi;
import me.kowo.opencity.providers.DataProvider;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

import static me.kowo.opencity.constants.Constants.BASE_URL;

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    DataProvider provideDataProvider(CityGuideApi cityGuideApi) {
        return new DataProvider(cityGuideApi);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    CityGuideApi getCityGuideApi(Retrofit retrofit) {
        return retrofit.create(CityGuideApi.class);
    }

}
