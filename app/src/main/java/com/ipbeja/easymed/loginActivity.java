package com.ipbeja.easymed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    loginBtn;

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
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        firebaseAuth = FirebaseAuth.getInstance();

        createAccountBtn = findViewById(R.id.createAccountBtn);
        createAccountBtn.setOnClickListener(v -> startActivity(
                new Intent(getApplicationContext(), registerActivity.class))
        );

        loginEmail = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);

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
