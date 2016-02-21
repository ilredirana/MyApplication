package weikenit.com.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartServiceReceiver extends BroadcastReceiver {
    public StartServiceReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,BackgroundService.class);
        context.startService(i);
    }
}
