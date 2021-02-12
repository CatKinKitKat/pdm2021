package com.ipbeja.easymed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The type Reset password.
 */
public class resetPassword extends AppCompatActivity {
    /**
     * The User password.
     */
    EditText userPassword,
    /**
     * The User conf password.
     */
    userConfPassword;
    /**
     * The Save password btn.
     */
    Button savePasswordBtn;
    /**
     * The User.
     */
    FirebaseUser user;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        userPassword = findViewById(R.id.newUserPassword);
        userConfPassword = findViewById(R.id.newConfirmPassword);

        user = FirebaseAuth.getInstance().getCurrentUser();

        savePasswordBtn = findViewById(R.id.resetPasswordBtn);
        savePasswordBtn.setOnClickListener(v -> {
            if (userPassword.getText().toString().isEmpty()) {

                userPassword.setError(getString(R.string.field_error));
                return;
            }

            if (userConfPassword.getText().toString().isEmpty()) {

                userConfPassword.setError(getString(R.string.field_error));
                return;
            }

            if (!userPassword.getText().toString().equals(userConfPassword.getText().toString())) {

                userConfPassword.setError(getString(R.string.passwd_error));
                return;
            }

            user.updatePassword(userPassword.getText().toString()).addOnSuccessListener(aVoid -> {

                Toast.makeText(resetPassword.this, getString(R.string.passwd_update), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), profileActivity.class));

                finish();
            }).addOnFailureListener(e -> Toast.makeText(

                    resetPassword.this, e.getMessage(), Toast.LENGTH_SHORT).show()
            );
        });
    }
}