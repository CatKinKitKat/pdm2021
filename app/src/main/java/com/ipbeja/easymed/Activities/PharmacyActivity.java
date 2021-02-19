package com.ipbeja.easymed.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ipbeja.easymed.Adapters.MedsRecyclerViewAdapter;
import com.ipbeja.easymed.FireStore.Doctors;
import com.ipbeja.easymed.FireStore.Meds;
import com.ipbeja.easymed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Pharmacy layout.
 */
public class PharmacyActivity extends AppCompatActivity {

    List<Meds> medData;
    /**
     * The Recycler view.
     */
    RecyclerView recyclerView;
    /**
     * The Helper adapter.
     */
    MedsRecyclerViewAdapter medRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_layout);

        this.recyclerView = findViewById(R.id.recyclerView);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.medData = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("meds").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Meds u = document.toObject(Meds.class);
                            u.setFireStoreId(document.getId());
                            this.medData.add(u);
                        }
                        this.medRecyclerViewAdapter = new MedsRecyclerViewAdapter(this.medData);
                        this.recyclerView.setAdapter(this.medRecyclerViewAdapter);
                    }
                }

        );

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