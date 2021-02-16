package com.ipbeja.easymed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ipbeja.easymed.FireStore.Users;


/**
 * The type Register activity.
 */
public class registerActivity extends AppCompatActivity {

    /**
     * The constant TAG.
     */
    public static final String TAG = "TAG";

    /**
     * The Register name, email, phone, passwd and confirmation fields
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
     * The Register user and go to login buttons
     */
    Button registerUserBtn, /**
     * The Go to login.
     */
    goToLogin;

    /**
     * Firebase objects.
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

        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        phoneNumber = findViewById(R.id.phoneNum);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfPass = findViewById(R.id.confPassword);
        registerUserBtn = findViewById(R.id.resetBtn);
        goToLogin = findViewById(R.id.goToLogin);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        goToLogin.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(), loginActivity.class));
            finish();
        });

        registerUserBtn.setOnClickListener(v -> {

            //extract the data
            String name = registerName.getText().toString();
            String email = registerEmail.getText().toString();
            String phone = phoneNumber.getText().toString();
            String password = registerPassword.getText().toString();
            String confPass = registerConfPass.getText().toString();

            if (name.isEmpty()) {

                registerName.setError(getString(R.string.name_field_error));
                return;
            }

            if (email.isEmpty()) {

                registerEmail.setError(getString(R.string.email_field_error));
                return;
            }

            if (password.isEmpty()) {

                registerPassword.setError(getString(R.string.passwd_field_error));
                return;
            }

            if (confPass.isEmpty()) {

                registerConfPass.setError(getString(R.string.confpasswd_field_error));
                return;
            }

            if (!password.equals(confPass)) {

                registerConfPass.setError(getString(R.string.passwd_error));
                return;
            }

            //data is validated
            //register the user using firebase
            fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {

                // send user to next page
                userID = fAuth.getCurrentUser().getUid();
                Users u = new Users(email, name, phone, "images/pill.png", userID);
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                db.collection("users").add(u)
                        .addOnSuccessListener(this, documentReference -> {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        });
            }).addOnFailureListener(e -> Toast.makeText(
                    registerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
            );
        });
    }
}
