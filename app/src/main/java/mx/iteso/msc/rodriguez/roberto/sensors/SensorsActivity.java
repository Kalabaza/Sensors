package mx.iteso.msc.rodriguez.roberto.sensors;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Roberto Rodriguez on 4/22/2016.
 */
public class SensorsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        TextView sensors = (TextView)findViewById(R.id.sensor_list);

        SensorManager manager = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        List<Sensor> list = manager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder data = new StringBuilder();
        for(Sensor sensor : list) {
            data.append("Name: " + sensor.getName() + "\n");
            data.append("Vendor: " + sensor.getVendor() + "\n");
            data.append("Version: " + sensor.getVersion() + "\n");
        }

        sensors.setText(data);
    }
}
