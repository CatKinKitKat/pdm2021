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
     * The Create account btn.
     */
    Button createAccountBtn,

    /**
     * The Login btn.
     */
    loginBtn,

    /**
     * The Forget password btn.
     */
    forget_password_btn;

    /**
     * The Login email.
     */
    EditText loginEmail,

    /**
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

        firebaseAuth = FirebaseAuth.getInstance();

        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        createAccountBtn = findViewById(R.id.createAccountBtn);
        createAccountBtn.setOnClickListener(v -> startActivity(
                new Intent(getApplicationContext(), registerActivity.class))
        );

        loginEmail = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        forget_password_btn = findViewById(R.id.forget_password_btn);
        forget_password_btn.setOnClickListener(v -> {

            // start alertdialog
            View view = inflater.inflate(R.layout.reset_pop, null);

            reset_alert.setTitle("Reset forgot password?")
                    .setMessage("Enter your email to get password reset link")
                    .setPositiveButton("Reset", (dialog, which) -> {

                        //validate the email address
                        EditText email = view.findViewById(R.id.reset_email_pop);
                        if (email.getText().toString().isEmpty()) {

                            email.setError("Required field");
                            return;
                        }
                        //send the reset link

                        firebaseAuth.sendPasswordResetEmail(email.getText().toString())
                                .addOnSuccessListener(aVoid -> Toast.makeText(
                                        loginActivity.this, "Reset email sent", Toast.LENGTH_SHORT).show()
                                ).addOnFailureListener(e -> Toast.makeText(
                                loginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
                        );

                    }).setNegativeButton("Cancel", null)
                    .setView(view)
                    .create().show();

        });

        loginBtn.setOnClickListener(v -> {

            //extract and validate
            if (loginEmail.getText().toString().isEmpty()) {

                loginEmail.setError("Email is Missing");
                return;
            }

            if (password.getText().toString().isEmpty()) {

                password.setError("Password is Missing");
                return;
            }

            //data is valid
            //login user
            firebaseAuth.signInWithEmailAndPassword(

                    loginEmail.getText().toString(),
                    password.getText().toString()).addOnSuccessListener(authResult -> {

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
