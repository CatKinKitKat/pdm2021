package com.ipbeja.easymed;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
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
     * The Covers.
     */
    int[] covers = new int[]{
            R.drawable.aaa,
            R.drawable.bbb,
            R.drawable.ccc,
            R.drawable.ddd,
            R.drawable.eee,
            R.drawable.fff
    };

    /**
     * The Slider view.
     */
    SliderView sliderView;

    /**
     * The Image slider model list.
     */
    List<ImageSliderModel> imageSliderModelList;

    /**
     * The Adapter.
     */
    private RecyclerViewAdapter adapter;

    /**
     * The Manager.
     */
    private StaggeredGridLayoutManager manager;

    /**
     * The App list.
     */
    private List<row> appList;

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Making the home button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu);

        //Initializing RecyclerView
        recyclerView = findViewById(R.id.recyclerview);
        appList = new ArrayList<>();

        //Adapter
        adapter = new RecyclerViewAdapter(this, appList);
        manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        //Layout Manager
        recyclerView.setLayoutManager(manager);

        //Adapter
        recyclerView.setAdapter(adapter);

        //Putting Data into recyclerView
        InitializeDataIntoRecyclerView();

        imageSliderModelList = new ArrayList<>();
        sliderView = findViewById(R.id.imageSlider);

        imageSliderModelList.add(new ImageSliderModel(R.drawable.image_a));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.image_b));
        imageSliderModelList.add(new ImageSliderModel(R.drawable.image_c));

        sliderView.setSliderAdapter(new ImageSliderAdapter(this, imageSliderModelList));
    }

    /**
     * Initialize data into recycler view.
     */
    private void InitializeDataIntoRecyclerView() {
        row a = new row(covers[0]);
        appList.add(a);

        a = new row(covers[1]);
        appList.add(a);

        a = new row(covers[2]);
        appList.add(a);

        a = new row(covers[3]);
        appList.add(a);

        a = new row(covers[4]);
        appList.add(a);

        a = new row(covers[5]);
        appList.add(a);

        adapter.notifyDataSetChanged();
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
            startActivity(new Intent(getApplicationContext(),loginActivity.class));
            finish();

        }else if (id == R.id.profile) {

            startActivity(new Intent(getApplicationContext(),profileActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }
}

