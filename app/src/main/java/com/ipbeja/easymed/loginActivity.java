package com.ipbeja.easymed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * The type Login activity.
 */
public class loginActivity extends AppCompatActivity {

    /**
     * The Create account, Login, passwd recover buttons.
     */
    Button createAccountBtn, /**
     * The Login btn.
     */
    loginBtn, /**
     * The Forget password btn.
     */
    forget_password_btn;

    /**
     * The Login email and passwd.
     */
    EditText loginEmail, /**
     * The Password.
     */
    password;

    /**
     * The Firebase auth.
     */
    FirebaseAuth firebaseAuth;

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
        setContentView(R.layout.login_activity);

        this.firebaseAuth = FirebaseAuth.getInstance();

        this.reset_alert = new AlertDialog.Builder(this);
        this.inflater = this.getLayoutInflater();

        this.createAccountBtn = findViewById(R.id.createAccountBtn);
        this.createAccountBtn.setOnClickListener(v -> startActivity(
                new Intent(getApplicationContext(), registerActivity.class))
        );

        this.loginEmail = findViewById(R.id.loginEmail);
        this.password = findViewById(R.id.loginPassword);
        this.loginBtn = findViewById(R.id.loginBtn);
        this.forget_password_btn = findViewById(R.id.forget_password_btn);
        this.forget_password_btn.setOnClickListener(v -> {

            // start alertdialog
            View view = this.inflater.inflate(R.layout.reset_pop, null);

            this.reset_alert.setTitle(getString(R.string.reset_passwd))
                    .setMessage(getString(R.string.email_reset_passwd))
                    .setPositiveButton(getString(R.string.reset), (dialog, which) -> {

                        //validate the email address
                        EditText email = view.findViewById(R.id.reset_email_pop);
                        if (email.getText().toString().isEmpty()) {

                            email.setError(getString(R.string.field_error));
                            return;
                        }
                        //send the reset link

                        this.firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                                .addOnSuccessListener(aVoid -> Toast.makeText(
                                        loginActivity.this, getString(R.string.reset_email_sent), Toast.LENGTH_SHORT).show()
                                ).addOnFailureListener(e -> Toast.makeText(
                                loginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
                        );

                    }).setNegativeButton(getString(R.string.cancel), null)
                    .setView(view)
                    .create().show();

        });

        this.loginBtn.setOnClickListener(v -> {

            //extract and validate
            if (this.loginEmail.getText().toString().isEmpty()) {

                this.loginEmail.setError(getString(R.string.email_login_missing));
                return;
            }

            if (this.password.getText().toString().isEmpty()) {

                this.password.setError(getString(R.string.passwd_login_missing));
                return;
            }

            //data is valid
            //login user
            this.firebaseAuth.signInWithEmailAndPassword(

                    this.loginEmail.getText().toString(),
                    this.password.getText().toString()).addOnSuccessListener(authResult -> {

                //login is successful
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }).addOnFailureListener(e -> Toast.makeText(

                    loginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
            );
        });
    }

    /**
     * On start.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
}
