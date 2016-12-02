package uk.co.andrespedraza.android.doubledecker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.Collections;
import java.util.List;

import uk.co.andrespedraza.android.doubledecker.R;
import uk.co.andrespedraza.android.doubledecker.network.domain.Prediction;
import uk.co.andrespedraza.android.doubledecker.network.domain.StopPoint;
import uk.co.andrespedraza.android.doubledecker.network.request.ListPredictionsRequest;

public class BusStopActivity extends DoubleDeckerActivity {

    public static final String INTENT_EXTRA_BUS_POINT = "BusStopActivity.INTENT_EXTRA_BUS_POINT";

    private StopPoint mStopPoint;

    private TextView mBusStopLetterTextView;
    private TextView mBusStopNameTextView;

    private RecyclerView mPredictionsRecyclerView;
    private PredictionAdapter mPredictionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);

        Intent intent = getIntent();
        if (null != intent) {

            mStopPoint = intent.getParcelableExtra(INTENT_EXTRA_BUS_POINT);

            mBusStopLetterTextView = (TextView) findViewById(R.id.bus_stop_letter);
            mBusStopNameTextView = (TextView) findViewById(R.id.bus_stop_name);
            mPredictionsRecyclerView = (RecyclerView) findViewById(R.id.predictions_recycler_view);
            mPredictionsRecyclerView.setHasFixedSize(true);
            mPredictionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            mBusStopLetterTextView.setText(mStopPoint.getStopLetter());
            mBusStopNameTextView.setText(mStopPoint.getCommonName());

        } else {
            showToast("No stop data.");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchBusStopPredictions();
    }

    private void fetchBusStopPredictions() {
        getSpiceManager().execute(
                new ListPredictionsRequest(mStopPoint.getNaptanId()),
                ListPredictionsRequest.CACHE_KEY,
                DurationInMillis.ALWAYS_EXPIRED,
                new RequestListener<Prediction.List>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        showSnackbar(R.id.activity_bus_stop, R.string.network_error, R.color.colorPrimary);
                    }

                    @Override
                    public void onRequestSuccess(Prediction.List predictions) {

                        if (null != predictions && !predictions.isEmpty()) {

                            Collections.sort(predictions);

                            if (null == mPredictionAdapter) {
                                mPredictionAdapter = new PredictionAdapter(predictions);
                                mPredictionsRecyclerView.setAdapter(mPredictionAdapter);
                            } else {
                                mPredictionAdapter.mPredictions = predictions;
                            }
                            mPredictionAdapter.notifyDataSetChanged();

                        } else {
                            showSnackbar(R.id.activity_bus_stop, R.string.no_predictions_to_show, R.color.colorPrimary);
                        }

                    }
                }
        );
    }

    public class PredictionHolder extends RecyclerView.ViewHolder {

        private Prediction mPrediction;

        private TextView mPredictionLineNumberTextView;
        private TextView mPredictionLineNameTextView;
        private TextView mPredictionTimeTextView;

        public PredictionHolder(View itemView) {
            super(itemView);

            mPredictionLineNumberTextView = (TextView) itemView.findViewById(R.id.prediction_line_number);
            mPredictionLineNameTextView = (TextView) itemView.findViewById(R.id.prediction_line_name);
            mPredictionTimeTextView = (TextView) itemView.findViewById(R.id.prediction_time);

            // TODO add on click listener to show all stops for this line
        }

        public void bindPrediction(Prediction prediction) {
            mPrediction = prediction;
            mPredictionLineNumberTextView.setText(mPrediction.getLineName());
            mPredictionLineNameTextView.setText(mPrediction.getDestinationName());
            mPredictionTimeTextView.setText(mPrediction.getMinValueString());
        }

    }

    private class PredictionAdapter extends RecyclerView.Adapter<PredictionHolder> {

        private List<Prediction> mPredictions;

        public PredictionAdapter(Prediction.List predictions) {
            this.mPredictions = predictions;
        }

        @Override
        public PredictionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(BusStopActivity.this);
            View mView = mLayoutInflater.inflate(R.layout.list_item_prediction, parent, false);
            return new PredictionHolder(mView);
        }

        @Override
        public void onBindViewHolder(PredictionHolder holder, int position) {
            holder.bindPrediction(this.mPredictions.get(position));
        }

        @Override
        public int getItemCount() {
            return this.mPredictions.size();
        }
    }
}
