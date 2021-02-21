package com.ipbeja.easymed.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ipbeja.easymed.FireStore.Users;
import com.ipbeja.easymed.R;


/**
 * The type Register activity.
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * The constant TAG.
     */
    public static final String TAG = "TAG";

    /**
     * The Register name.
     */
    EditText registerName, /**
     * The Register email.
     */
    registerEmail, /**
     * The Register password.
     */
    registerPassword, /**
     * The Register conf pass.
     */
    registerConfPass, /**
     * The Phone number.
     */
    phoneNumber;

    /**
     * The Register user btn.
     */
    Button registerUserBtn, /**
     * The Go to login.
     */
    goToLogin;

    /**
     * The F auth.
     */
    FirebaseAuth fAuth;
    /**
     * The F store.
     */
    FirebaseFirestore fStore;

    /**
     * The User id.
     */
    String userID;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        this.registerName = findViewById(R.id.registerName);
        this.registerEmail = findViewById(R.id.registerEmail);
        this.phoneNumber = findViewById(R.id.phoneNum);
        this.registerPassword = findViewById(R.id.registerPassword);
        this.registerConfPass = findViewById(R.id.confPassword);
        this.registerUserBtn = findViewById(R.id.resetBtn);
        this.goToLogin = findViewById(R.id.goToLogin);

        this.fAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();

        this.goToLogin.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        });

        registerUserBtn.setOnClickListener(v -> {

            //extract the data
            String name = this.registerName.getText().toString();
            String email = this.registerEmail.getText().toString();
            String phone = this.phoneNumber.getText().toString();
            String password = this.registerPassword.getText().toString();
            String confPass = this.registerConfPass.getText().toString();

            if (name.isEmpty()) {

                this.registerName.setError(getString(R.string.name_field_error));
                return;
            }

            if (email.isEmpty()) {

                this.registerEmail.setError(getString(R.string.email_field_error));
                return;
            }

            if (password.isEmpty()) {

                this.registerPassword.setError(getString(R.string.passwd_field_error));
                return;
            }

            if (confPass.isEmpty()) {

                this.registerConfPass.setError(getString(R.string.confpasswd_field_error));
                return;
            }

            if (!password.equals(confPass)) {

                this.registerConfPass.setError(getString(R.string.passwd_error));
                return;
            }

            //data is validated
            //register the user using firebase
            this.fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {

                // send user to next page
                this.userID = this.fAuth.getCurrentUser().getUid();
                Users u = new Users(email, name, phone, "images/pill.png", this.userID);
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("users").add(u)
                        .addOnSuccessListener(this, documentReference -> {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        });
            }).addOnFailureListener(e -> Toast.makeText(
                    RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
            );
        });
    }
}
