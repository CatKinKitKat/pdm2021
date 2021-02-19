
package com.ipbeja.easymed.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.ipbeja.easymed.R;


public class CardListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_cart;
    TextView txt_final_price;
    Button btn_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
    }
}