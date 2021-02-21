package com.ipbeja.easymed.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.ipbeja.easymed.Adapters.DoctorRecyclerViewAdapter;
import com.ipbeja.easymed.FireStore.Doctors;
import com.ipbeja.easymed.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Doctors activity.
 */
public class DoctorsActivity extends AppCompatActivity {

    /**
     * The Doctors data.
     */
    List<Doctors> doctorsData;

    /**
     * The Recycler view.
     */
    RecyclerView recyclerView;

    /**
     * The Doctor recycler view adapter.
     */
    DoctorRecyclerViewAdapter doctorRecyclerViewAdapter;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_layout);

        this.recyclerView = findViewById(R.id.recyclerview);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.doctorsData = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("doctors").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Doctors u = document.toObject(Doctors.class);
                            u.setFireStoreId(document.getId());
                            this.doctorsData.add(u);
                        }
                        this.doctorRecyclerViewAdapter = new DoctorRecyclerViewAdapter(this.doctorsData);
                        this.recyclerView.setAdapter(this.doctorRecyclerViewAdapter);
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