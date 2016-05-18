package mx.iteso.msc.rodriguez.roberto.sensors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sensors = (Button)findViewById(R.id.btn_details);
        sensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SensorsActivity.class);
                startActivity(intent);
            }
        });
        Button location = (Button)findViewById(R.id.btn_location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
        Button accelerometer = (Button)findViewById(R.id.btn_accelerometer);
        accelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccelerometerActivity.class);
                startActivity(intent);
            }
        });
        Button compass = (Button)findViewById(R.id.btn_compass);
        compass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CompassActivity.class);
                startActivity(intent);
            }
        });
    }
}
