package com.ipbeja.easymed.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ipbeja.easymed.R;

/**
 * The type Pharmacy layout.
 */
public class CheckoutActivity extends AppCompatActivity {

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.kart));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button clearButton = findViewById(R.id.clearKartBtn);
        Button buyButton = findViewById(R.id.buyBtn);

        buyButton.setOnClickListener(v -> {
            Toast.makeText(
                    CheckoutActivity.this,
                    getString(R.string.mbway_error),
                    Toast.LENGTH_SHORT
            ).show();
        });

        clearButton.setOnClickListener(v -> {

            // TODO: CLEAR RECYCLERVIEW
        });
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