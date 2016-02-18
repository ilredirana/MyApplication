package weikenit.com.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //获取系统配置
    SharedPreferences preferences;
    //服务器ip地址
    String serverIP;
    EditText edit_ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        edit_ip = (EditText) findViewById(R.id.edit_ip);
        preferences = getSharedPreferences("config",MODE_PRIVATE);
        serverIP = preferences.getString("server_ip",null);
        if (!(null==serverIP)){
            edit_ip.setText(serverIP);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                Intent intent = new Intent(this,BackgroundService.class);
                startService(intent);
                Snackbar.make(v, "后台服务已启动", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.change_ip:
                String newIP = edit_ip.getText().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("server_ip",newIP);
                editor.apply();
                serverIP = newIP;
                break;
        }
    }
}
