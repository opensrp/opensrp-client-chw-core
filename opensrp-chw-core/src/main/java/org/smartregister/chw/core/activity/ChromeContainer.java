package org.smartregister.chw.core.activity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import org.smartregister.chw.core.R;

import static org.smartregister.chw.core.utils.CoreConstants.INTENT_KEY.CONTENT_TO_DISPLAY;

public class ChromeContainer extends AppCompatActivity {
    protected AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrome_container);

        Toolbar toolbar = findViewById(R.id.back_to_nav_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setTitle(getResources().getString(R.string.care_plan));
        }

        if (getIntent().hasExtra(CONTENT_TO_DISPLAY) &&
                getIntent().getStringExtra(CONTENT_TO_DISPLAY) != null) {
            String contentToDisplay = getIntent().getStringExtra(CONTENT_TO_DISPLAY);
            WebView webView = findViewById(R.id.web_view);
            webView.loadData(contentToDisplay, "text/html", "utf-8");
        }
    }
}