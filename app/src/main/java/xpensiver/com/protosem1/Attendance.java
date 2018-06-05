package xpensiver.com.protosem1;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Attendance extends AppCompatActivity {
    private String time,date, rollno, now_date, response;
    private SharedPreferences saved_roll_no;
    private Button button;
    private  TextView response1, present, absent , notavailable, todayattendance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        button = (Button)findViewById(R.id.attendance);
        saved_roll_no = getApplicationContext().getSharedPreferences("address", MODE_PRIVATE);
        final String address1=saved_roll_no.getString("roll_no", "");
        DateFormat df = new SimpleDateFormat("dd/mm ,HH");
        response1 = (TextView) findViewById(R.id.responseTextView);
        present = (TextView) findViewById(R.id.presentid);
        absent = (TextView) findViewById(R.id.absentid);
        notavailable = (TextView) findViewById(R.id.not_availableid);
        todayattendance = (TextView) findViewById(R.id.todays_attendance);

        String connected_ssid = getCurrentSsid(getApplicationContext());
        Toast.makeText(getApplicationContext(),connected_ssid,Toast.LENGTH_LONG).show();
        Log.e("connection ssid", connected_ssid );
        if(connected_ssid.equals("BOLT") ){
            Toast.makeText(getApplicationContext(),"connected to bolt",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"connect to bolt",Toast.LENGTH_LONG).show();
        }
        Date dateobj = new Date();
        date  = df.format(dateobj);
        String[] keyValue = date.split(",");
        time = keyValue[1];
        now_date = keyValue[0];



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.valueOf(time) >= 8 && Integer.valueOf(time) <= 11){
                   // Client myClient = new Client("192.168.59.39", 8081, "p" , rollno , response1);
                    Client1 myclient1 = new Client1("192.168.55.81",8082,response1,address1+",1","p",present,absent,notavailable,todayattendance);
                    myclient1.execute();
                    Toast.makeText(getApplicationContext(),"session 1",Toast.LENGTH_LONG).show();
                }else if(Integer.parseInt(time)>= 11 && Integer.parseInt(time) <= 13){
                    Client1 myclient1 = new Client1("192.168.55.81",8082,response1,address1+",2","p",present,absent,notavailable,todayattendance);
                    myclient1.execute();
                    Toast.makeText(getApplicationContext(),"session 2",Toast.LENGTH_LONG).show();
                } else if(Integer.parseInt(time)>= 14 && Integer.parseInt(time) <= 16) {
                    Client1 myclient1 = new Client1("192.168.55.81", 8082, response1, address1+",3", "p", present, absent, notavailable, todayattendance);
                    myclient1.execute();
                    Toast.makeText(getApplicationContext(),"session 3",Toast.LENGTH_LONG).show();
                } else if(Integer.parseInt(time)>= 16 && Integer.parseInt(time) <= 19) {
                    Client1 myclient1 = new Client1("192.168.55.81", 8082, response1, address1+",4", "p", present, absent, notavailable, todayattendance);
                    myclient1.execute();
                    Toast.makeText(getApplicationContext(),"session 4",Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplicationContext(),"invalid time",Toast.LENGTH_LONG).show();
                    response1.setText("invalid time");

                }
            }
        });
    }
    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
            }
        }
        return ssid;
    }
}

