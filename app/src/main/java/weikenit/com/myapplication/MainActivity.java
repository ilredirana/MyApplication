package weikenit.com.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //获取系统配置
    SharedPreferences preferences;
    //服务器ip地址
    String serverIP;
    EditText edit_ip;
    boolean isServiceRunning;
    FloatingActionButton fab;
    Button changeIp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        changeIp = (Button) findViewById(R.id.change_ip);
        changeIp.setOnClickListener(this);
        edit_ip = (EditText) findViewById(R.id.edit_ip);
        preferences = getSharedPreferences("config",MODE_PRIVATE);
        serverIP = preferences.getString("server_ip",null);
        if (!(null==serverIP)){
            edit_ip.setText(serverIP);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                if (null==serverIP){
                    Toast.makeText(this,"请先设置服务器IP",Toast.LENGTH_SHORT).show();
                    edit_ip.requestFocus();
                    return;
                }
                if (isServiceRunning){
                    Intent intent = new Intent(this,BackgroundService.class);
                    stopService(intent);
                    Snackbar.make(v, "后台服务已停止", Snackbar.LENGTH_LONG).show();
                    isServiceRunning = false;
                    fab.setImageResource(R.drawable.ic_play_light);
                }else {
                    Intent intent = new Intent(this,BackgroundService.class);
                    startService(intent);
                    Snackbar.make(v, "后台服务已启动", Snackbar.LENGTH_LONG).show();
                    fab.setImageResource(R.drawable.ic_pause_light);
                    isServiceRunning = true;
                }
                break;
            case R.id.change_ip:
                String newIP = edit_ip.getText().toString();
                Log.d("__","asdasdasdasdasda");
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("server_ip",newIP);
                editor.apply();
                serverIP = newIP;
                break;
        }
    }
}
