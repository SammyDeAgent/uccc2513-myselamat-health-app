package cyto.iridium.mselamat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class LaunchScreen extends AppCompatActivity {

    Handler mHandler;
    Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);

        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(
                        LaunchScreen.this,
                       GoogleLoginAuth.class
                );
                startActivity(intent);
                finish();
            }
        };
        mHandler.postDelayed(mRunnable,2000);
    }
}