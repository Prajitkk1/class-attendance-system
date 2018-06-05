package xpensiver.com.protosem1;

/**
 * Created by prajit kk on 10/28/2017.
 */

import android.os.AsyncTask;
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

public class Client extends AsyncTask<Void, Void, String> {

    String dstAddress,number1,formattedDate, response1;
    int dstPort;
    String response = "";
    TextView textResponse1;

    Client(String addr, int port, String textResponse , String number , TextView responseattend) {
        dstAddress = addr;
        dstPort = port;
        response1 = textResponse;
        number1 =number;
        textResponse1 = responseattend;
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
                DOS.writeUTF(number1 +","+ response1);
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
        textResponse1.setText(response);
        super.onPostExecute(result);
    }
}