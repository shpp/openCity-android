package me.kowo.opencity.dialogs;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.kowo.opencity.R;

public class SocialTaxiDialog extends DialogFragment {

    final String LOG_TAG = "myLogs";

    private Button yesButton;
    private TextView socialTaxiPhoneNumber1;
    private TextView socialTaxiPhoneNumber2;
    private TextView fortressEmailAddress;
    private TextView podilEmailAddress;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.social_taxi_dialog, null);

        yesButton = (Button)v.findViewById(R.id.yesButton);
        yesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG, "Dialog 1: " + yesButton.getText());
                dismiss();
            }
        });

        socialTaxiPhoneNumber1 = (TextView)v.findViewById(R.id.socialTaxiPhoneNumber1);
        socialTaxiPhoneNumber1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onCall1();
            }
        });

        socialTaxiPhoneNumber2 = (TextView)v.findViewById(R.id.socialTaxiPhoneNumber2);
        socialTaxiPhoneNumber2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onCall2();
            }
        });

        fortressEmailAddress = (TextView)v.findViewById(R.id.fortressEmailAddress);
        fortressEmailAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMailFortress();
            }
        });

        podilEmailAddress = (TextView)v.findViewById(R.id.podilEmailAddress);
        podilEmailAddress.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMailPodil();
            }
        });

        return v;
    }



    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(LOG_TAG, "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d(LOG_TAG, "Dialog 1: onCancel");
    }

    public void onCall1() {
        try {
            String number = "0996662941";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        } catch (Exception e) {
            Log.e("Application", "Failed to invoke call", e);
        }
    }

    public void onCall2() {
        try {
            String number = "0522220867";
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        } catch (Exception e) {
            Log.e("Application", "Failed to invoke call", e);
        }
    }

    private void sendMailFortress() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","kirtercentr@mail.ru", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void sendMailPodil() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","len.upszn@krmr.gov.ua", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

}
