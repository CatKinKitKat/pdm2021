package com.ipbeja.easymed.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ipbeja.easymed.R;

/**
 * The type Reminder activity.
 */
public class ReminderActivity extends AppCompatActivity {

    /**
     * The M cursor adapter.
     */
    AlarmCursorAdapter mCursorAdapter;
    /**
     * The Reminder list view.
     */
    ListView reminderListView;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);


        reminderListView = findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        reminderListView.setEmptyView(emptyView);

        mCursorAdapter = new AlarmCursorAdapter(this, null);
        reminderListView.setAdapter(mCursorAdapter);

        reminderListView.setOnItemClickListener((adapterView, view, position, id) -> {

            Intent intent = new Intent(ReminderActivity.this, AddReminderActivity.class);

            startActivity(intent);

        });

        FloatingActionButton mAddReminderButton = findViewById(R.id.fab);

        mAddReminderButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddReminderActivity.class);
            startActivity(intent);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.reminder_title));
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