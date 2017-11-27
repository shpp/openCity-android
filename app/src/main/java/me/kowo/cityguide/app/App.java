package me.kowo.cityguide.app;

import javax.inject.Singleton;

import dagger.Component;
import me.kowo.cityguide.activities.MainActivity;

public class App extends android.app.Application {

    ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
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
    }
}
