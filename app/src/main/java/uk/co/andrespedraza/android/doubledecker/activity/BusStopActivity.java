package uk.co.andrespedraza.android.doubledecker.activity;

import android.content.Intent;
import android.os.Bundle;

import uk.co.andrespedraza.android.doubledecker.R;
import uk.co.andrespedraza.android.doubledecker.network.domain.StopPoint;

public class BusStopActivity extends DoubleDeckerActivity {

    public static final String INTENT_EXTRA_BUS_POINT = "BusStopActivity.INTENT_EXTRA_BUS_POINT";

    private StopPoint mStopPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);

        Intent intent = getIntent();
        if (null != intent) {
            mStopPoint = intent.getParcelableExtra(INTENT_EXTRA_BUS_POINT);
            showToast("Stop selected: " + mStopPoint.getCommonName());
        } else {
            showToast("No stop data.");
        }

    }
}
