package me.kowo.opencity.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import me.kowo.opencity.R;



public class LowTrolleyBusDialog extends DialogFragment implements View.OnClickListener {
    final String LOG_TAG = "myLogs";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.low_trolley_bus_dialog, null);
        v.findViewById(R.id.yesButton).setOnClickListener(this);
        v.findViewById(R.id.trolleyPhoneNumber).setOnClickListener(this);
        return v;
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.yesButton:
                Log.d(LOG_TAG, "Dialog 1: " + ((Button) v).getText());
                dismiss();
                break;
            case R.id.trolleyPhoneNumber:
                onCall();

                break;
            default:
                break;
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    public void onCall() {
        try {
            String number = "0957260939";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        } catch (Exception e) {
            Log.e("Application", "Failed to invoke call", e);
        }
    }
}