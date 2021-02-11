package com.ipbeja.easymed;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

/**
 * The type Profile activity.
 */
public class profileActivity extends AppCompatActivity {

    /**
     * The Verify msg.
     */
    TextView verifyMsg;

    /**
     * The Verify email btn.
     */
    Button verifyEmailBtn, resetBtn;

    /**
     * The Auth.
     */
    FirebaseAuth auth;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        auth = FirebaseAuth.getInstance();
        verifyMsg = findViewById(R.id.verifyEmailMsg);
        verifyEmailBtn = findViewById(R.id.verifyEmailBtn);
        resetBtn = findViewById(R.id.resetBtn);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!auth.getCurrentUser().isEmailVerified()) {

            verifyEmailBtn.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);

        }

        verifyEmailBtn.setOnClickListener(v -> {
            //send verification email
            auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(aVoid -> {
                Toast.makeText(profileActivity.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                verifyEmailBtn.setVisibility(View.GONE);
                verifyMsg.setVisibility(View.GONE);
                FirebaseAuth.getInstance().signOut();
                profileActivity.this.startActivity(new Intent(profileActivity.this.getApplicationContext(), loginActivity.class));
                profileActivity.this.finish();
            });
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), resetPassword.class));
            }
        });

    }

    /**
     * On create options menu boolean.
     *
     * @param menu the menu
     * @return the boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    /**
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), loginActivity.class));
            finish();

        } else if (id == android.R.id.home) {

            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }

        return super.onOptionsItemSelected(item);
    }


}
