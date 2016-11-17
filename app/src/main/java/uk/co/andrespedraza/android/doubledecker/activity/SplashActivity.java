package uk.co.andrespedraza.android.doubledecker.activity;

import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends DoubleDeckerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, NearbyActivity.class);
        startActivity(intent);
        finish();
    }

}
