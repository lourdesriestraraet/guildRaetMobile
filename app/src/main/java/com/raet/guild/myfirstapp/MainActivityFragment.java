package com.raet.guild.myfirstapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivityFragment extends Fragment implements View.OnClickListener {
    private TextView tv_greet;
    private Button bt_greet;
    private EditText ed_greet;

    private static int countNotif = 0;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        tv_greet = (TextView) rootView.findViewById(R.id.main_greet_tv);
        bt_greet = (Button) rootView.findViewById(R.id.main_greet_buton);
        ed_greet = (EditText) rootView.findViewById(R.id.main_name_greet_et);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_greet.setVisibility(View.GONE);

        bt_greet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == bt_greet.getId()) {
            if (ed_greet.getText().toString().isEmpty()) {
                tv_greet.setText(getActivity().getResources().getString(R.string.main_error));
                tv_greet.setVisibility(View.VISIBLE);
            } else {
                String myMessage = getActivity().getResources().getString(R.string.main_greet_two)
                        + " "
                        + ed_greet.getText().toString()
                        + " "
                        + getActivity().getResources().getString(R.string.main_greet_one);
                sendNotification(ed_greet.getText().toString());
                tv_greet.setText(myMessage);
                tv_greet.setVisibility(View.VISIBLE);
            }
        }
    }

    private void sendNotification(final String myMessage) {
        int notificationId = 001;
        // Build intent for notification content
        Intent viewIntent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        viewIntent.putExtra("myNotification", 1);
        PendingIntent viewPendingIntent =
                PendingIntent.getActivity(getActivity().getApplicationContext(), 0, viewIntent, 0);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.ic_stat_name)
                        .setContentTitle(getActivity().getResources().getString(R.string.app_name))
                        .setContentText(myMessage + getActivity().getResources().getString(R.string.main_notification_message))
                        .setContentIntent(viewPendingIntent)
                        .setAutoCancel(true)
                        .setNumber(++countNotif);

        // Get an instance of the NotificationManager service
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(getActivity().getApplicationContext());

        // Build the notification and issues it with notification manager.
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
}
