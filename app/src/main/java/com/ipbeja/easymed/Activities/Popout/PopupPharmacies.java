package com.ipbeja.easymed.Activities.Popout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ipbeja.easymed.R;

/**
 * The type Popup meds.
 */
public class PopupPharmacies extends AppCompatActivity {

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_pharmacy);

        Intent i = getIntent();
        String url = i.getStringExtra("url");

        WebView webView = findViewById(R.id.webview);

        configWebView(webView, url);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Meds");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    /**
     * Config web view.
     *
     * @param webView the web view
     * @param url     the url
     */
    private void configWebView(WebView webView, String url) {

        webView.loadUrl(url);

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