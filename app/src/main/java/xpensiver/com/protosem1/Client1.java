package xpensiver.com.protosem1;

/**
 * Created by prajit kk on 10/28/2017.
 */

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Client1 extends AsyncTask<Void, Void, String> {

    String dstAddress,number1,formattedDate , attended;
    int dstPort;
    String response = "";
    TextView textResponse , presentno, absentno , notavailable , todays_attendance;

    Client1(String addr, int port, TextView textResponse , String number , String pppp , TextView presentno , TextView absentno, TextView notavailable , TextView todays_attendance) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
        number1 =number;
        attended = pppp;
        this.presentno = presentno;
        this.absentno = absentno;
        this.notavailable = notavailable;
        this.todays_attendance = todays_attendance;

    }

    @Override
    protected String doInBackground(Void... arg0) {
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("dd");
        formattedDate = df.format(c);

        Socket socket = null;
        while (true) {
            try {
                socket = new Socket(dstAddress, dstPort);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                        1024);
                byte[] buffer = new byte[1024];

                int bytesRead;
                InputStream inputStream = socket.getInputStream();
                DataOutputStream DOS = new DataOutputStream(socket.getOutputStream());
                DOS.writeUTF(number1 +","+ attended);

			/*
             * notice: inputStream.read() will block if no data return
			 */
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);
                    response += byteArrayOutputStream.toString("UTF-8");
                }

                Log.i("string", response);
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } //finally {
            //if (socket != null) {
            //  try {
            //    socket.close();
            //    } catch (IOException e) {
            // TODO Auto-generated catch block
            //        e.printStackTrace();
            //      }
            //   }
            //   }
            return response;

        }
    }

    @Override
    protected void onPostExecute(String result) {
        final String values[] = response.split(",");
        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                textResponse.setText(values[0]);
                presentno.setText(values[1]);
                absentno.setText(values[2]);
                notavailable.setText(values[3]);
                todays_attendance.setText(values[4]);
            }
        }, secondsDelayed * 1000);

        super.onPostExecute(result);
    }

}