package com.ipbeja.easymed.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ipbeja.easymed.R;

/**
 * The type Reset password activity.
 */
public class ResetPasswordActivity extends AppCompatActivity {
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

        this.userPassword = findViewById(R.id.newUserPassword);
        this.userConfPassword = findViewById(R.id.newConfirmPassword);

        this.user = FirebaseAuth.getInstance().getCurrentUser();

        this.savePasswordBtn = findViewById(R.id.resetPasswordBtn);
        this.savePasswordBtn.setOnClickListener(v -> {
            if (this.userPassword.getText().toString().isEmpty()) {

                this.userPassword.setError(getString(R.string.field_error));
                return;
            }

            if (this.userConfPassword.getText().toString().isEmpty()) {

                this.userConfPassword.setError(getString(R.string.field_error));
                return;
            }

            if (!this.userPassword.getText().toString().equals(this.userConfPassword.getText().toString())) {

                this.userConfPassword.setError(getString(R.string.passwd_error));
                return;
            }

            this.user.updatePassword(this.userPassword.getText().toString()).addOnSuccessListener(aVoid -> {

                Toast.makeText(ResetPasswordActivity.this, getString(R.string.passwd_update), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                finish();
            }).addOnFailureListener(e -> Toast.makeText(

                    ResetPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
            );
        });
    }
}