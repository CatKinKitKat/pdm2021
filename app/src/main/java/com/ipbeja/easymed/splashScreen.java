package com.ipbeja.easymed;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The type Splash screen.
 */
public class splashScreen extends AppCompatActivity {

    /**
     * The constant SPLASH_TIMEOUT.
     */
    private static final long SPLASH_TIMEOUT = 1000;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {

            Intent intent = new Intent(splashScreen.this, loginActivity.class);

            startActivity(intent);
            finish();
        }, SPLASH_TIMEOUT);
    }

}
