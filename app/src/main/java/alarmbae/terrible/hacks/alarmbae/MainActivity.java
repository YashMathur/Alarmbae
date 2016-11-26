package alarmbae.terrible.hacks.alarmbae;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.GvrActivity;
import com.google.vr.sdk.base.GvrView;
import com.google.vr.sdk.base.HeadTransform;
import com.google.vr.sdk.base.Viewport;

import javax.microedition.khronos.egl.EGLConfig;

public class MainActivity extends GvrActivity implements GvrView.StereoRenderer {

    AlarmManager alarm_manager;
    TimePicker timePicker;
    ImageButton startButton;
    PendingIntent pendingIntent;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Initialize views
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        startButton = (ImageButton)findViewById(R.id.startButton);
        alarm_manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        cancelButton = (Button)findViewById(R.id.cancel);

        final Calendar calendar = Calendar.getInstance();

        final Intent my_intent = new Intent(this, AlarmReceiver.class);

        //Functionality
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

                int hour = timePicker.getHour();
                int min = timePicker.getMinute();

                String hour_str = Integer.toString(hour);
                String min_str = Integer.toString(min);

                if(hour > 12)
                    hour_str = String.valueOf(hour - 12);

                if(min < 10)
                    min_str = "0" + Integer.toString(min);

                Toast.makeText(MainActivity.this, "Alarm set to: " + hour_str +":" + min_str, Toast.LENGTH_SHORT).show();

                my_intent.putExtra("extra", "alarm on");


                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Alarm cancelled :(", Toast.LENGTH_SHORT).show();

                alarm_manager.cancel(pendingIntent);

                //How to stop the alarm: for YASH
                //my_intent.putExtra("extra, "alarm off");
                // Put this line where the user turns off the alarm
                //sendBroadcast(my_intent);
                //Put that line where the alarm should turn off
            }
        });

    }

    @Override
    public void onNewFrame(HeadTransform headTransform) {

    }

    @Override
    public void onDrawEye(Eye eye) {

    }

    @Override
    public void onFinishFrame(Viewport viewport) {

    }

    @Override
    public void onSurfaceChanged(int i, int i1) {

    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {

    }

    @Override
    public void onRendererShutdown() {

    }
}
