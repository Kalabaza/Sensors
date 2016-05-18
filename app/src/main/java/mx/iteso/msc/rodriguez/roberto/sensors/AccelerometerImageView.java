package mx.iteso.msc.rodriguez.roberto.sensors;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Roberto Rodriguez on 4/22/2016.
 */
public class AccelerometerImageView extends ImageView implements SensorEventListener {
    private Context mContext;
    private Handler mHandler;
    private final int FRAME_RATE = 30;
    private float xCoordinate = 0, yCoordinate = 0, lastX = 0, lastY = 0;
    private int xVelocity = 10, yVelocity = 10;

    private boolean mInitialized;
    private final float NOISE_X = 0.3f;
    private final float NOISE_Y = 0.1f;

    Sensor accelerometer;
    SensorManager sensorManager;

    public AccelerometerImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mInitialized = false;
        mContext = context;
        mHandler = new Handler();
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    protected void onDraw(Canvas canvas) {
        BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.ball);
        canvas.drawBitmap(ball.getBitmap(), xCoordinate, yCoordinate, null);
        mHandler.postDelayed(runnable, FRAME_RATE);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.ball);
            float curX = sensorEvent.values[0];
            float curY = sensorEvent.values[1];
            if (!mInitialized) {
                lastX = curX;
                lastY = curY;
                mInitialized = true;
            } else {
                float deltaX = Math.abs(lastX - curX);
                float deltaY = Math.abs(lastY - curY);
                if (deltaX < NOISE_X)
                    deltaX = 0.0f;
                if (deltaY < NOISE_Y)
                    deltaY = 0.0f;
                lastX = curX;
                lastY = curY;
                if (deltaX != 0.0f) {
                    // Left direction
                    if (curX < 0)
                        xCoordinate += xVelocity;
                        // Right direction
                    else
                        xCoordinate -= xVelocity;
                }
                if (deltaY != 0.0f) {
                    // Down direction
                    if (curY < 0)
                        yCoordinate += yVelocity;
                        // Up direction
                    else
                        yCoordinate -= yVelocity;
                }
                if (xCoordinate > this.getWidth() - ball.getBitmap().getWidth())
                    xCoordinate = this.getWidth() - ball.getBitmap().getWidth();
                else if (xCoordinate < 0)
                    xCoordinate = 0;
                if (yCoordinate > this.getHeight() - ball.getBitmap().getHeight())
                    yCoordinate = this.getHeight() - ball.getBitmap().getHeight();
                else if (yCoordinate < 0)
                    yCoordinate = 0;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
