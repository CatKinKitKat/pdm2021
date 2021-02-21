package com.ipbeja.easymed.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ipbeja.easymed.FireStore.Users;
import com.ipbeja.easymed.R;

import java.io.ByteArrayOutputStream;
import java.util.Objects;
import java.util.UUID;

/**
 * The type Profile activity.
 */
public class ProfileActivity extends AppCompatActivity {

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
    private TextView verifyMsg,
    /**
     * The Name.
     */
    name,
    /**
     * The Email.
     */
    email,
    /**
     * The Phone.
     */
    phone;

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

        this.fAuth = FirebaseAuth.getInstance();
        this.verifyMsg = findViewById(R.id.verifyEmailMsg);

        this.verifyEmailBtn = findViewById(R.id.verifyEmailBtn);
        Button resetBtn = findViewById(R.id.resetBtn);
        Button deleteAccountBtn = findViewById(R.id.delete_account_menu);

        this.name = findViewById(R.id.profileName);
        this.email = findViewById(R.id.profileEmail);
        this.phone = findViewById(R.id.profileNumber);

        this.profileImage = findViewById(R.id.profileImage);
        Button changeProfile = findViewById(R.id.changeProfile);
        FirebaseStorage.getInstance().getReference();
        String userID = this.fAuth.getCurrentUser().getUid();

        if (userID == null) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("authID", userID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            Users u = document.toObject(Users.class);
                            u.setFireStoreID(document.getId());
                            this.user = u;
                        }
                        if (this.user != null) {
                            this.phone.setText(this.user.getPhone());
                            this.name.setText(this.user.getfName());
                            this.email.setText(this.user.getEmail());

                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReference();
                            StorageReference picturePath = storageRef.child(this.user.getProfileImagePath());

                            picturePath.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                                this.profileImage.setImageBitmap(
                                        BitmapFactory.decodeByteArray(bytes, 0, bytes.length)
                                );
                            });
                        }
                    }
                });

        this.reset_alert = new AlertDialog.Builder(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.profile));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!this.fAuth.getCurrentUser().isEmailVerified()) {

            this.verifyEmailBtn.setVisibility(View.VISIBLE);
            this.verifyMsg.setVisibility(View.VISIBLE);
        }

        this.verifyEmailBtn.setOnClickListener(v -> {


            this.fAuth.getCurrentUser().sendEmailVerification().addOnSuccessListener(aVoid -> {

                Toast.makeText(
                        ProfileActivity.this, getString(R.string.email_verify), Toast.LENGTH_SHORT
                ).show();

                this.verifyEmailBtn.setVisibility(View.GONE);
                this.verifyMsg.setVisibility(View.GONE);

                FirebaseAuth.getInstance().signOut();
                ProfileActivity.this.startActivity(new Intent(
                        ProfileActivity.this.getApplicationContext(), LoginActivity.class)
                );
                ProfileActivity.this.finish();
            });
        });

        resetBtn.setOnClickListener(v -> startActivity(

                new Intent(getApplicationContext(), ResetPasswordActivity.class))
        );

        deleteAccountBtn.setOnClickListener(v -> {

            this.reset_alert.setTitle(getString(R.string.del_acc_prompt))
                    .setMessage(getString(R.string.conf_prompt))
                    .setPositiveButton(getString(R.string.ok), (dialog, which) -> {

                        FirebaseUser user = this.fAuth.getCurrentUser();
                        user.delete().addOnSuccessListener(aVoid -> {

                            Toast.makeText(ProfileActivity.this,
                                    getString(R.string.acc_del),
                                    Toast.LENGTH_SHORT).show();
                            db.collection("users").document(this.user.getFireStoreID())
                                    .delete()
                                    .addOnSuccessListener(aVoid1 -> Log.d(TAG, getString(R.string.del_acc_success)))
                                    .addOnFailureListener(e -> Log.w(TAG, getString(R.string.del_acc_error), e));

                            this.fAuth.signOut();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }).addOnFailureListener(e -> Toast.makeText(
                                ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show()
                        );
                    }).setNegativeButton(getString(R.string.cancel), null).create().show();
        });


        changeProfile.setOnClickListener(v -> {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, CAMERA_REQUEST_CODE);
        });
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

        this.profileImage.setDrawingCacheEnabled(true);
        this.profileImage.buildDrawingCache();

        Bitmap bitmap = ((BitmapDrawable) this.profileImage.getDrawable()).getBitmap();
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
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        } else if (id == android.R.id.home) {

            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}