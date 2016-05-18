package mx.iteso.msc.rodriguez.roberto.sensors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Roberto Rodriguez on 4/22/2016.
 */
public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    Float azimut;  // View to draw a compass

    CustomDrawableView mCustomDrawableView;
    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor magnetometer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        mCustomDrawableView = new CustomDrawableView(this);
        setContentView(mCustomDrawableView);    // Register the sensor listeners
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    public class CustomDrawableView extends View {
        Paint paint = new Paint();

        public CustomDrawableView(Context context) {
            super(context);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(2);
            paint.setAntiAlias(true);
            paint.setTextSize(40);
        }

        ;

        protected void onDraw(Canvas canvas) {
            int width = getWidth();
            int height = getHeight();
            int centerx = width / 2;
            int centery = height / 2;
            // Draw the background circle
            canvas.drawCircle(centerx, centery, centerx, paint);
            // Rotate the canvas with the azimut
            if (azimut != null)
                canvas.rotate(-azimut * 360 / (2 * 3.14159f), centerx, centery);
            // Draw the segment for the north
            paint.setColor(Color.RED);
            canvas.drawLine(centerx, centery / 2, centerx, centery, paint);
            canvas.drawText("N", centerx - 15, centery / 2, paint);
            // Draw the segment for the south
            paint.setColor(Color.BLUE);
            canvas.drawLine(centerx, centery, centerx, height - centery / 2, paint);
            canvas.drawText("S", centerx - 15, centery + centery / 2 + 30, paint);
            paint.setColor(Color.BLACK);
        }
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    float[] mGravity;
    float[] mGeomagnetic;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values.clone();
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values.clone();
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimut = orientation[0]; // orientation contains: azimut, pitch and roll
            }
        }
        mCustomDrawableView.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
