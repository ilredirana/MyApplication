package weikenit.com.myapplication;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;

public class BackgroundService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //TODO 前台服务
        Notification notification = new Notification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO: 用百度地图sdk发送位置信息
            }
        }).start();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        SharedPreferences preferences = getSharedPreferences("config",MODE_PRIVATE);
        int interval = preferences.getInt("interval",30);
        int time = interval*1000;//30秒
        long triggerAtTime = SystemClock.elapsedRealtime()+time;
        Intent newIntent = new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,newIntent,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }
}
