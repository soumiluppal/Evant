package com.cs307.evant.evant;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.cs307.evant.evant.MainActivity.db;

public class NotifcationsService extends Service {
    /*public NotifcationsService() {
    }*/

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("BEFORE");
        if (db.getUid() != null) {
            System.out.println("AFTER");
            ArrayList<String> myEvents = db.getMyEvents(db.getUid());
            ArrayList<String> myDescrips = new ArrayList<>();
            ArrayList<String> myLoc = new ArrayList<>();
            ArrayList<String> myTime = new ArrayList<>();
            ArrayList<String> myHst = new ArrayList<>();

            ArrayList<String> titles = db.getTitles();
            ArrayList<String> descrips = db.getDescription();
            ArrayList<String> loc = db.getLoc();
            ArrayList<String> dtTime = db.getTime();
            ArrayList<String> hst = db.getHost();
            ArrayList<String> upcomingTitles = new ArrayList<>();
            for (int a = 0; a < myEvents.size(); a++) {
                for (int b = 0; b < titles.size(); b++) {
                    if (myEvents.get(a) == titles.get(b)) {
                        Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy   hh:mm aa");
                        String formattedDate = df.format(currentTime);
                        String eventTime = dtTime.get(b);
                        formattedDate = formattedDate.toUpperCase();

                        //
                        String curfirst, curlast, eventfirst, eventlast, curd, eventd;
                        curfirst = formattedDate.substring(0, formattedDate.indexOf('/'));
                        eventfirst = eventTime.substring(0, eventTime.indexOf('/'));
                        curlast = formattedDate.substring(5, formattedDate.length());
                        eventlast = eventTime.substring(5, eventTime.length());

                        System.out.println(curlast + " :CURR: " + curfirst);
                        System.out.println(eventlast + " :EVENT: " + eventfirst);
                        if (!eventfirst.equals(curfirst) || !eventlast.equals(curlast)) {
                            continue;
                        }
                        curd = formattedDate.substring(3, 5);
                        eventd = eventTime.substring(3, 5);
                        int dd = Integer.parseInt(curd) + 1;
                        int d = Integer.parseInt(eventd);
                        System.out.println(dd + ":::" + d);
                        if (dd == d) {
                            upcomingTitles.add(titles.get(b));
                            myDescrips.add(descrips.get(b));
                            myLoc.add(loc.get(b));
                            myTime.add(dtTime.get(b));
                            myHst.add(hst.get(b));
                        }

                    }
                }
            }
            if(upcomingTitles.size() > 0) {
                System.out.println("YO");
                int notifyID = 1;
                String CHANNEL_ID = "my_channel_01";// The id of the channel.
                CharSequence name = "evantNotification";// The user-visible name of the channel.
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
// Create a notification and set the notification channel.
                Notification notification = new Notification.Builder(NotifcationsService.this)
                        .setContentTitle("Event Reminder")
                        .setContentText(upcomingTitles.get(0) + " tomorrow at " + myTime.get(0).substring(myTime.get(0).indexOf(' ')+2, myTime.get(0).length()) + " at " + myLoc.get(0))
                        .setSmallIcon(R.drawable.logoevant)
                        .setChannelId(CHANNEL_ID)
                        .build();
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.createNotificationChannel(mChannel);

// Issue the notification.
                mNotificationManager.notify(notifyID, notification);
            }
        }
        return START_STICKY;
    }
}
