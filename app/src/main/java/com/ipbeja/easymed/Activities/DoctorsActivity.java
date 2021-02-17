package com.ipbeja.easymed.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ipbeja.easymed.FireStore.Doctors;
import com.ipbeja.easymed.R;
import com.ipbeja.easymed.Adapters.HelperAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Doctors layout.
 */
public class DoctorsActivity extends AppCompatActivity {

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    List<Doctors> doctorsData;
    /**
     * The Recycler view.
     */
    RecyclerView recyclerView;
    /**
     * The Helper adapter.
     */
    HelperAdapter helperAdapter;
    /**
     * The Database reference.
     */
    DatabaseReference databaseReference;


    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_layout);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorsData = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("doctors").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Doctors u = document.toObject(Doctors.class);
                            u.setFireStoreId(document.getId());
                            doctorsData.add(u);
                        }
                        helperAdapter = new HelperAdapter(doctorsData);
                        recyclerView.setAdapter(helperAdapter);
                    }
                }

        );

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.find_doctors));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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