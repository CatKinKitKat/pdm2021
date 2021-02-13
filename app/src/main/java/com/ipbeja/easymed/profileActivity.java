package com.ipbeja.easymed;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ipbeja.easymed.FireStore.Users;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

/**
 * The type Profile activity.
 */
public class profileActivity extends AppCompatActivity {

    /**
     * The Verify msg.
     */
    public static final String TAG = "TAG";

    /**
     * The constant CAMERA_REQUEST_CODE.
     */
    public static final int CAMERA_REQUEST_CODE = 3000;

    /**
     * The User.
     */
    private Users user;

    /**
     * The Verify message, name email and phone.
     */
    private TextView verifyMsg, name, email, phone;

    /**
     * The Verify email btn.
     */
    private Button verifyEmailBtn;


    /**
     * The Auth.
     */
    private FirebaseAuth fAuth;

    /**
     * The Reset alert.
     */
    private AlertDialog.Builder reset_alert;

    /**
     * The Inflater.
     */
    private LayoutInflater inflater;

    /**
     * The Profile image.
     */
    private ImageView profileImage;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        fAuth = FirebaseAuth.getInstance();
        verifyMsg = findViewById(R.id.verifyEmailMsg);

        verifyEmailBtn = findViewById(R.id.verifyEmailBtn);
        Button resetBtn = findViewById(R.id.resetBtn);
        //Button updateEmailMenu = findViewById(R.id.updateEmailMenu);
        Button deleteAccountBtn = findViewById(R.id.delete_account_menu);

        name = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profileNumber);

        profileImage = findViewById(R.id.profileImage);
        Button changeProfile = findViewById(R.id.changeProfile);

        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        FirebaseStorage.getInstance().getReference();
        String userID = fAuth.getCurrentUser().getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("authID", userID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        Users u = null;

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            u = document.toObject(Users.class);
                            u.setFireStoreID(document.getId());
                        }

                        if (u != null) {

                            this.user = u;

                            phone.setText(u.getPhone());
                            name.setText(u.getfName());
                            email.setText(u.getEmail());

                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReference();
                            StorageReference picturePath = storageRef.child(u.getProfileImagePath());

                            picturePath.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                                this.profileImage.setImageBitmap(
                                        BitmapFactory.decodeByteArray(bytes, 0, bytes.length)
                                );
                            });
                        }
                    }
                });

        DocumentReference documentReference = fStore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, (documentSnapshot, e) -> {
            if (e != null) {

                Log.d(TAG, "Error" + e.getMessage());
            } else {

                phone.setText(documentSnapshot.getString("phone"));
                name.setText(documentSnapshot.getString("fName"));
                email.setText(documentSnapshot.getString("email"));
            }
        });

        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.profile));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!fAuth.getCurrentUser().isEmailVerified()) {

            verifyEmailBtn.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);
        }

        verifyEmailBtn.setOnClickListener(v -> {

            //send verification email
            fAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(aVoid -> {

                Toast.makeText(profileActivity.this, getString(R.string.email_verify), Toast.LENGTH_SHORT).show();

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

            reset_alert.setTitle(getString(R.string.del_acc_prompt))
                    .setMessage(getString(R.string.conf_prompt))
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                        FirebaseUser user = fAuth.getCurrentUser();
                        user.delete().addOnSuccessListener(aVoid -> {

                            Toast.makeText(profileActivity.this,
                                    getString(R.string.acc_del),
                                    Toast.LENGTH_SHORT).show();
                            db.collection("users").document(this.user.getFireStoreID())
                                    .delete()
                                    .addOnSuccessListener(aVoid1 -> Log.d(TAG, getString(R.string.del_acc_success)))
                                    .addOnFailureListener(e -> Log.w(TAG, getString(R.string.del_acc_error), e));

                            fAuth.signOut();
                            startActivity(new Intent(getApplicationContext(), loginActivity.class));
                            finish();
                        }).addOnFailureListener(e -> Toast.makeText(
                                profileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
                        );
                    }).setNegativeButton(getString(R.string.cancel), null).create().show();
        });


        changeProfile.setOnClickListener(v -> {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, CAMERA_REQUEST_CODE);
        });

        /*updateEmailMenu.setOnClickListener(v -> {

            View view = inflater.inflate(R.layout.reset_pop, null);

            reset_alert.setTitle(getString(R.string.update_email_prompt))
                    .setMessage(getString(R.string.email_input_prompt))
                    .setPositiveButton(getString(R.string.update), (dialog, which) -> {

                        EditText email = view.findViewById(R.id.reset_email_pop);
                        if (email.getText().toString().isEmpty()) {

                            email.setError(getString(R.string.field_error));
                            return;
                        }

                        FirebaseUser user = fAuth.getCurrentUser();
                        user.updateEmail(email.getText().toString()).addOnSuccessListener(aVoid -> Toast.makeText(
                                profileActivity.this, getString(R.string.email_update), Toast.LENGTH_SHORT).show()
                        ).addOnFailureListener(e -> Toast.makeText(
                                profileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
                        );

                    }).setNegativeButton(getString(R.string.cancel), null).setView(view).create().show();
        });*/


    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = data.getParcelableExtra("data");
                this.profileImage.setImageBitmap(bitmap);

                uploadImageToFirebase();
            } else {
                Toast.makeText(this, getString(R.string.cancel_action), Toast.LENGTH_SHORT).show();
            }
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Upload image to firebase.
     */
    private void uploadImageToFirebase() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String imagePath = "images/" + UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef = storageRef.child(imagePath);

        profileImage.setDrawingCacheEnabled(true);
        profileImage.buildDrawingCache();

        Bitmap bitmap = ((BitmapDrawable) profileImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        imageRef.putBytes(data);

        this.user.setProfileImagePath(imagePath);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(this.user.getFireStoreID()).set(this.user);

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