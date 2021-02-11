package com.ipbeja.easymed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;


/**
 * The type Register activity.
 */
public class registerActivity extends AppCompatActivity {

    EditText registerName, registerEmail, registerPassword, registerConfPass;
    Button registerUserBtn, goToLogin;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConfPass = findViewById(R.id.confPassword);
        registerUserBtn = findViewById(R.id.registerBtn);
        goToLogin = findViewById(R.id.goToLogin);

        fAuth = FirebaseAuth.getInstance();

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),loginActivity.class));
                finish();
            }
        });

        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //extract the data

                String name = registerName.getText().toString();
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                String confPass = registerConfPass.getText().toString();

                if(name.isEmpty()){

                    registerName.setError("Name is required");
                    return;

                }

                if(email.isEmpty()){

                    registerEmail.setError("Email is required");
                    return;

                }

                if(password.isEmpty()){

                    registerPassword.setError("Password is required");
                    return;

                }

                if(confPass.isEmpty()){

                    registerConfPass.setError("Confirmation Password is required");
                    return;

                }

                if(!password.equals(confPass)){

                    registerConfPass.setError("Password do not match");
                    return;

                }

                //data is validated
                //register the user using firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
                    // send user to next page
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


    }
}
