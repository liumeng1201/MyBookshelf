package com.smartjinyu.mybookshelf;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


/**
 * about page
 * Created by smartjinyu on 2017/2/7.
 */

public class AboutActivity extends AppCompatActivity {
    private static final String TAG = "Aboutctivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.about_toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.about_preference_category_title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            AboutFragment aboutFragment = new AboutFragment();
            getFragmentManager().beginTransaction().add(R.id.activity_about_container, aboutFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
