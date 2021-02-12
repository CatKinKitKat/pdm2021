package com.ipbeja.easymed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    Button verifyEmailBtn,
    /**
     * The Reset btn.
     */
    resetBtn,
    /**
     * The Update email menu.
     */
    updateEmailMenu,
    /**
     * The Delete account btn.
     */
    deleteAccountBtn;

    /**
     * The Auth.
     */
    FirebaseAuth auth;

    /**
     * The Reset alert.
     */
    AlertDialog.Builder reset_alert;

    /**
     * The Inflater.
     */
    LayoutInflater inflater;

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
        updateEmailMenu = findViewById(R.id.updateEmailMenu);
        deleteAccountBtn = findViewById(R.id.delete_account_menu);

        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

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
                profileActivity.this.startActivity(new Intent(
                        profileActivity.this.getApplicationContext(), loginActivity.class)
                );
                profileActivity.this.finish();
            });
        });

        resetBtn.setOnClickListener(v -> startActivity(

                new Intent(getApplicationContext(), resetPassword.class))
        );

        deleteAccountBtn.setOnClickListener(v -> {

            reset_alert.setTitle("Delete account permanently?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Ok", (dialog, which) -> {

                        FirebaseUser user = auth.getCurrentUser();
                        user.delete().addOnSuccessListener(aVoid -> {

                            Toast.makeText(profileActivity.this, "Account deleted", Toast.LENGTH_SHORT).show();
                            auth.signOut();

                            startActivity(new Intent(getApplicationContext(), loginActivity.class));
                            finish();
                        }).addOnFailureListener(e -> Toast.makeText(
                                profileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
                        );
                    }).setNegativeButton("Cancel", null).create().show();
        });


        updateEmailMenu.setOnClickListener(v -> {

            View view = inflater.inflate(R.layout.reset_pop, null);

            reset_alert.setTitle("Update Email?")
                    .setMessage("Enter now email address")
                    .setPositiveButton("Update", (dialog, which) -> {

                        EditText email = view.findViewById(R.id.reset_email_pop);
                        if (email.getText().toString().isEmpty()) {

                            email.setError("Required field");
                            return;
                        }

                        FirebaseUser user = auth.getCurrentUser();
                        user.updateEmail(email.getText().toString()).addOnSuccessListener(aVoid -> Toast.makeText(
                                profileActivity.this, "Email updated", Toast.LENGTH_SHORT).show()
                        ).addOnFailureListener(e -> Toast.makeText(
                                profileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
                        );

                    }).setNegativeButton("Cancel", null).setView(view).create().show();
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

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
