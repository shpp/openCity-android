package me.kowo.cityguide.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.kowo.cityguide.R;
import me.kowo.cityguide.app.App;
import me.kowo.cityguide.models.Establishment;
import me.kowo.cityguide.providers.DataProvider;

public class MainActivity extends AppCompatActivity {
    final public static String LOG_TAG = "myLogs";


    @Inject
    DataProvider dataProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((App) getApplicationContext()).getAppComponent().inject(this);
        ButterKnife.bind(this);
        printDataArray();
    }

    //For test only
    public void printDataArray() {
        for (Establishment et : dataProvider.getEstablishmentsList()) {
            Log.v(LOG_TAG, et.name + " : " + et.address);
            Toast.makeText(this, "Button is clicked: " + et.name + " : " + et.address, Toast.LENGTH_SHORT).show();
        }
    }
}
