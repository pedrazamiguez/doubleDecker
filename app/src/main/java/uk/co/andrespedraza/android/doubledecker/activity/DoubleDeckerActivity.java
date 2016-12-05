package uk.co.andrespedraza.android.doubledecker.activity;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;

import uk.co.andrespedraza.android.doubledecker.R;
import uk.co.andrespedraza.android.doubledecker.network.service.TfLService;

public class DoubleDeckerActivity extends AppCompatActivity {

    private SpiceManager mSpiceManager = new SpiceManager(TfLService.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    protected void onStart() {
        mSpiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mSpiceManager.shouldStop();
        super.onStop();
    }

    public SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

    /**
     * Shows a toast with the given text.
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackbar(@IdRes int viewId, @StringRes int stringId, @ColorRes int colorId) {
        View parentView = findViewById(viewId);
        Snackbar snackbar = Snackbar.make(parentView, getString(stringId), Snackbar.LENGTH_SHORT)
                .setAction("Action", null);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, colorId));
        snackbar.show();
    }
}
