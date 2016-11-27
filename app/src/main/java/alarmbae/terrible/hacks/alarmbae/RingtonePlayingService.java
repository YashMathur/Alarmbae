package alarmbae.terrible.hacks.alarmbae;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Daniel on 2016-11-26.
 */

public class RingtonePlayingService extends Service {

    MediaPlayer mp;
    PendingIntent pI;
    Boolean isRunning = false;
    int startId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Log.i("LocalService", "Received start id " + startId + ": " + intent);
        Intent in = new Intent(this, MainActivity.class);
        String state = intent.getExtras().getString("extra");
        assert state != null;
        switch (state) {
            case "alarm off":
                startId = 0;
                break;
            case "alarm on":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }

        if(!this.isRunning && startId == 1){
            String[] songNames = {"doge", "harambe", "reggae", "troll_face", "trump"};

            Random rand = new Random();
            int  n = rand.nextInt(4);

            String song = songNames[n];

            int songID = getResources().getIdentifier(song, "raw", getPackageName());
            mp = MediaPlayer.create(this, songID);
            mp.setLooping(true);
            mp.start();
            MainActivity.mmm = 1;

            this.isRunning = true;
            this.startId = 0;

        } else if (this.isRunning && startId == 0){
            mp.stop();
            mp.reset();
            Log.d("Stopping alarm", "1");
            this.isRunning = false;
            this.startId = 0;
        }




        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Toast.makeText(this, "On Destroy called", Toast.LENGTH_SHORT).show();

        super.onDestroy();
        this.isRunning = false;
    }
}
