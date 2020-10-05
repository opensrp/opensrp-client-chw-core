package org.smartregister.chw.core.activity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.AppBarLayout;

import org.smartregister.chw.core.R;

import static org.smartregister.chw.core.utils.CoreConstants.INTENT_KEY.CONTENT_TO_DISPLAY;

public class WebViewActivity extends AppCompatActivity {
    protected AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        initObjects();
        if (getIntent().hasExtra(CONTENT_TO_DISPLAY) &&
                getIntent().getStringExtra(CONTENT_TO_DISPLAY) != null) {
            String contentToDisplay = getIntent().getStringExtra(CONTENT_TO_DISPLAY);
            WebView webView = findViewById(R.id.web_view);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadDataWithBaseURL(null, contentToDisplay, "text/html", "utf-8", null);
        } else {
            showNoContentAlertDialog();
        }
    }


    private void initObjects() {
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(view -> WebViewActivity.this.finish());
    }

    private void showNoContentAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("No Content Found");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Close",
                (dialog, which) -> {
                    dialog.dismiss();
                    this.finish();
                });
        alertDialog.show();
    }
}