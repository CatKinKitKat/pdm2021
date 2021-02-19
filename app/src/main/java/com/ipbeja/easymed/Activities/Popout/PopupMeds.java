package com.ipbeja.easymed.Activities.Popout;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ipbeja.easymed.Adapters.MedsRecyclerViewAdapter;
import com.ipbeja.easymed.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * The type Popup meds.
 */
public class PopupMeds extends AppCompatActivity {

    /**
     * The Name holder.
     */
    TextView nameHolder, /**
     * The Price holder.
     */
    priceHolder;
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
        setContentView(R.layout.activity_popup_meds);

        nameHolder = findViewById(R.id.medName);
        priceHolder = findViewById(R.id.priceText);
        circleImage = findViewById(R.id.desc_img);

        Intent i = getIntent();
        String name = i.getStringExtra("name");
        String price = i.getStringExtra("price");

        nameHolder.setText(name);
        priceHolder.setText(price);
        circleImage.setImageDrawable(new BitmapDrawable(getResources(), MedsRecyclerViewAdapter.getBitmap_transfer()));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Meds");
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