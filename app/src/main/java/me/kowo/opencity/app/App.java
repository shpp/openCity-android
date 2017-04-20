package me.kowo.opencity.app;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import javax.inject.Singleton;

import dagger.Component;
import me.kowo.opencity.activities.MainActivity;
import me.kowo.opencity.fragments.MapsFragment;

public class App extends android.app.Application {

    ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mComponent = DaggerApp_ApplicationComponent.builder()
                .appModule(new AppModule(this))
                .build();

        getAppComponent().inject(this);
    }

    public ApplicationComponent getAppComponent() {
        return mComponent;
    }

    @Singleton
    @Component(modules = AppModule.class)
    public interface ApplicationComponent {

        void inject(App application);

        void inject(MainActivity mainActivity);

        void inject(MapsFragment mapsFragment);
    }
}
