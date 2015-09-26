package mobi.tattu.hola.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import mobi.tattu.hola.R;
import mobi.tattu.hola.ui.HolaLaNacionApplication;

public class PushReceiver extends BroadcastReceiver {

    public PushReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification n = new NotificationCompat.Builder(context)
                .setContentTitle("Último Momento")
                .setContentText("Choque fatal en Panamericana")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(123, n);

        HolaLaNacionApplication.mPush = true;
    }
}
