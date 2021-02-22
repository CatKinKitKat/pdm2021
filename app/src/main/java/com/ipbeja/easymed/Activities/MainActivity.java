package com.ipbeja.easymed.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.ipbeja.easymed.Adapters.ImageSliderAdapter;
import com.ipbeja.easymed.Adapters.MainRecyclerViewAdapter;
import com.ipbeja.easymed.Objects.ImageSlider;
import com.ipbeja.easymed.Objects.Row;
import com.ipbeja.easymed.R;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The Recycler view.
     */
    RecyclerView recyclerView;

    /**
     * The Slider view.
     */
    SliderView sliderView;

    /**
     * The Image slider list.
     */
    List<ImageSlider> imageSliderList;

    /**
     * The Adapter.
     */
    private MainRecyclerViewAdapter adapter;

    /**
     * The App list.
     */
    private List<Row> appList;
    /**
     * The App list text.
     */
    private List<String> appListText;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        this.recyclerView = findViewById(R.id.recyclerview);
        this.appList = new ArrayList<>();
        this.appListText = new ArrayList<>();


        this.adapter = new MainRecyclerViewAdapter(this, this.appList, this.appListText);
        StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        int[] covers = new int[]{
                R.drawable.aaa,
                R.drawable.bbb,
                R.drawable.ccc,
                R.drawable.ddd
        };

        String[] txts = new String[]{
                getString(R.string.reminder_title),
                getString(R.string.easy_med_order),
                getString(R.string.hospitals),
                getString(R.string.find_doctors)
        };

        InitializeDataIntoRecyclerView(covers, txts);
        this.recyclerView.setLayoutManager(manager);
        this.recyclerView.setAdapter(this.adapter);

        this.imageSliderList = new ArrayList<>();
        this.sliderView = findViewById(R.id.imageSlider);

        this.imageSliderList.add(new ImageSlider(R.drawable.image_a));
        this.imageSliderList.add(new ImageSlider(R.drawable.image_b));
        this.imageSliderList.add(new ImageSlider(R.drawable.image_c));

        this.sliderView.setSliderAdapter(new ImageSliderAdapter(this, this.imageSliderList));
    }

    /**
     * Initialize data into recycler view.
     */
    private void InitializeDataIntoRecyclerView(int[] covers, String[] txts) {

        Row a;
        String b;

        for (int cover : covers) {
            a = new Row(cover);
            this.appList.add(a);
        }

        for (String txt : txts) {
            b = String.valueOf(txt);
            this.appListText.add(b);
        }

        this.adapter.notifyDataSetChanged();
    }

    /**
     * On create options menu boolean.
     *
     * @param menu the menu
     * @return the boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        } else if (id == R.id.profile) {

            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

