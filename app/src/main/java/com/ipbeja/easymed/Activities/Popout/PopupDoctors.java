package com.ipbeja.easymed.Activities.Popout;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ipbeja.easymed.Adapters.DoctorRecyclerViewAdapter;
import com.ipbeja.easymed.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * The type Popup doctors.
 */
public class PopupDoctors extends AppCompatActivity {

    /**
     * The Name holder.
     */
    TextView nameHolder, /**
     * The Email holder.
     */
    emailHolder, /**
     * The Phone number holder.
     */
    phoneNumberHolder, /**
     * The Speciality holder.
     */
    specialityHolder;
    /**
     * The Circle image.
     */
    CircleImageView circleImage;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_doctors);

        nameHolder = findViewById(R.id.doctorName);
        emailHolder = findViewById(R.id.doctorEmail);
        specialityHolder = findViewById(R.id.doctorSpeciality);
        phoneNumberHolder = findViewById(R.id.doctorPhoneNumb);
        circleImage = findViewById(R.id.desc_img);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String speciality = i.getStringExtra("speciality");
        String email = i.getStringExtra("email");
        String phoneNumb = i.getStringExtra("phoneNumber");

        nameHolder.setText(name);
        specialityHolder.setText(speciality);
        emailHolder.setText(email);
        phoneNumberHolder.setText(phoneNumb);
        circleImage.setImageDrawable(new BitmapDrawable(getResources(), DoctorRecyclerViewAdapter.getBitmap_transfer()));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Doctors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}