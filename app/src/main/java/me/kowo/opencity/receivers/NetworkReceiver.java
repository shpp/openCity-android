package me.kowo.opencity.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;

import me.kowo.opencity.eventbus.Event;
import me.kowo.opencity.eventbus.EventMessage;

public class NetworkReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if ((netInfo != null && netInfo.isConnected())) {
            EventBus.getDefault().post(new Event()
                    .setEventMessage(EventMessage.ON_INTERNET_CONNECTED));
        }
    }
}
