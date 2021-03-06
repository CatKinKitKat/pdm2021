package com.ipbeja.easymed.Activities.Popout;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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

        this.nameHolder = findViewById(R.id.doctorName);
        this.emailHolder = findViewById(R.id.doctorEmail);
        this.specialityHolder = findViewById(R.id.doctorSpeciality);
        this.phoneNumberHolder = findViewById(R.id.doctorPhoneNumb);
        this.circleImage = findViewById(R.id.desc_img);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String speciality = i.getStringExtra("speciality");
        String email = i.getStringExtra("email");
        String phoneNumb = i.getStringExtra("phoneNumber");

        this.nameHolder.setText(name);
        this.specialityHolder.setText(speciality);
        this.emailHolder.setText(email);
        this.phoneNumberHolder.setText(phoneNumb);
        this.circleImage.setImageDrawable(new BitmapDrawable(getResources(), DoctorRecyclerViewAdapter.getBitmap_transfer()));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.doctor_info));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.phoneNumberHolder.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + this.phoneNumberHolder.getText()));
            startActivity(intent);
        });

        this.emailHolder.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:" + this.emailHolder.getText()));
            startActivity(intent);
        });

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