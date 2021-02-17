package com.ipbeja.easymed.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ipbeja.easymed.R;

/**
 * The type Pharmacy layout.
 */
public class PharmacyActivity extends AppCompatActivity {

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.easy_med_order));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button myKartBtn = findViewById(R.id.checkoutBtn);

        myKartBtn.setOnClickListener(v -> {

            this.goToKart();
        });
    }

    /**
     * Go to kart.
     */
    private void goToKart() {

        startActivity(new Intent(getApplicationContext(), CheckoutActivity.class));
    }

    /**
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}