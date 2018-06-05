package xpensiver.com.protosem1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class getting_rollno extends AppCompatActivity {
    private EditText rollno;
    private Button button;
    private SharedPreferences saved_roll_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_rollno);
        rollno = (EditText)findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roll_no = rollno.getText().toString();
                saved_roll_no = getApplicationContext().getSharedPreferences("address", MODE_PRIVATE);
                String address1=saved_roll_no.getString("roll_no", "");
                SharedPreferences.Editor editor = saved_roll_no.edit();
                editor.putString("roll_no", roll_no);
                editor.apply();
                Toast.makeText(getApplicationContext(),"address submitted",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getting_rollno.this, Attendance.class));

            }
        });
    }
}


