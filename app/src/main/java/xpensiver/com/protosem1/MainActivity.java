package xpensiver.com.protosem1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                        .getBoolean("isFirstRun", true);

                if (isFirstRun) {
                    //show start activity

                    startActivity(new Intent(MainActivity.this, getting_rollno.class));
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                            .putBoolean("isFirstRun", false).commit();

                }else{
                    startActivity(new Intent(MainActivity.this, Attendance.class));
                }
            }
        }, secondsDelayed * 1000);
    }
}
