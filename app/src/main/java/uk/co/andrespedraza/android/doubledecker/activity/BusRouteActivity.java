package uk.co.andrespedraza.android.doubledecker.activity;

import android.content.Context;
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

import java.util.List;

import uk.co.andrespedraza.android.doubledecker.R;
import uk.co.andrespedraza.android.doubledecker.network.domain.RouteSequence;
import uk.co.andrespedraza.android.doubledecker.network.domain.StopPoint;
import uk.co.andrespedraza.android.doubledecker.network.domain.enums.LineRouteDirection;
import uk.co.andrespedraza.android.doubledecker.network.request.ListLineRouteRequest;

public class BusRouteActivity extends DoubleDeckerActivity {

    private static final String BUNDLE_ARG_LINE_NUMBER = "uk.co.andrespedraza.android.doubledecker.activity.BUNDLE_ARG_LINE_NUMBER";
    private static final String BUNDLE_ARG_LINE_NAME = "uk.co.andrespedraza.android.doubledecker.activity.BUNDLE_ARG_LINE_NAME";
    private static final String BUNDLE_ARG_LINE_VIA = "uk.co.andrespedraza.android.doubledecker.activity.BUNDLE_ARG_LINE_VIA";
    private static final String BUNDLE_ARG_LINE_NAPTANDID = "uk.co.andrespedraza.android.doubledecker.activity.BUNDLE_ARG_LINE_NAPTANDID";

    private String mLineNumber;
    private String mLineName;
    private String mLineVia;
    private String mLineNaptandId;

    private TextView mLineNumberTextView;
    private TextView mLineNameTextView;
    private TextView mLineViaTextView;

    private RecyclerView mLineRouteRecyclerView;
    private RouteAdapter mRouteAdapter;

    public static Intent newInstance(Context context, String lineNumber, String lineName, String lineVia, String naptadId) {
        Intent newIntent = new Intent(context, BusRouteActivity.class);
        newIntent.putExtra(BUNDLE_ARG_LINE_NUMBER, lineNumber);
        newIntent.putExtra(BUNDLE_ARG_LINE_NAME, lineName);
        newIntent.putExtra(BUNDLE_ARG_LINE_VIA, null != lineVia && !lineVia.trim().isEmpty() ? String.format("towards %s", lineVia.trim()) : lineVia);
        newIntent.putExtra(BUNDLE_ARG_LINE_NAPTANDID, naptadId);

        return newIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route);

        mLineNumberTextView = (TextView) findViewById(R.id.bus_line_number);
        mLineNameTextView = (TextView) findViewById(R.id.bus_line_name);
        mLineViaTextView = (TextView) findViewById(R.id.bus_line_via);
        mLineRouteRecyclerView = (RecyclerView) findViewById(R.id.bus_line_route);
        mLineRouteRecyclerView.setHasFixedSize(true);
        mLineRouteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        if (null != intent) {
            mLineNumber = intent.getStringExtra(BUNDLE_ARG_LINE_NUMBER);
            mLineName = intent.getStringExtra(BUNDLE_ARG_LINE_NAME);
            mLineVia = intent.getStringExtra(BUNDLE_ARG_LINE_VIA);
            mLineNaptandId = intent.getStringExtra(BUNDLE_ARG_LINE_NAPTANDID);

            mLineNumberTextView.setText(mLineNumber);
            mLineNameTextView.setText(mLineName);
            mLineViaTextView.setText(mLineVia);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchLineRoute();
    }

    private void fetchLineRoute() {
        getSpiceManager().execute(
                new ListLineRouteRequest(this.mLineNumber, LineRouteDirection.OUTBOUND),
                ListLineRouteRequest.CACHE_KEY,
                DurationInMillis.ALWAYS_EXPIRED,
                new RequestListener<RouteSequence>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {
                        showSnackbar(R.id.activity_bus_stop, R.string.network_error, R.color.colorPrimary);
                    }

                    @Override
                    public void onRequestSuccess(RouteSequence routeSequence) {

                        if (null != routeSequence && null != routeSequence.getStopPointSequences()
                                && !routeSequence.getStopPointSequences().isEmpty()
                                && null != routeSequence.getStopPointSequences().get(0).getStopPoint()
                                && !routeSequence.getStopPointSequences().get(0).getStopPoint().isEmpty()) {

                            if (null == mRouteAdapter) {
                                mRouteAdapter = new RouteAdapter(routeSequence);
                                mLineRouteRecyclerView.setAdapter(mRouteAdapter);
                            } else {
                                mRouteAdapter.mStopPoints = routeSequence.getStopPointSequences().get(0).getStopPoint();
                            }
                            mRouteAdapter.notifyDataSetChanged();

                        } else {
                            showSnackbar(R.id.activity_bus_route, R.string.no_routes_to_show, R.color.colorPrimary);
                        }

                    }
                }
        );
    }

    public class RouteHolder extends RecyclerView.ViewHolder {

        private StopPoint mStopPoint;

        private TextView mStopPointLetterTextView;
        private TextView mStopPointNameTextView;
        private TextView mStopPointLinesTextView;

        public RouteHolder(View itemView) {
            super(itemView);

            mStopPointNameTextView = (TextView) itemView.findViewById(R.id.stop_point_name);
            mStopPointLetterTextView = (TextView) itemView.findViewById(R.id.stop_point_letter);
            mStopPointLinesTextView = (TextView) itemView.findViewById(R.id.stop_point_lines);
        }

        public void bindRoute(StopPoint stopPoint) {
            this.mStopPoint = stopPoint;
            mStopPointNameTextView.setText(mStopPoint.getName());
            mStopPointLetterTextView.setText(mStopPoint.getStopLetter());
            mStopPointLinesTextView.setText(mStopPoint.generateLinesList());
        }
    }

    private class RouteAdapter extends RecyclerView.Adapter<RouteHolder> {

        private List<StopPoint> mStopPoints;

        public RouteAdapter(RouteSequence routeSequence) {
            this.mStopPoints = routeSequence.getStopPointSequences().get(0).getStopPoint();
        }

        @Override
        public RouteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(BusRouteActivity.this);
            View mView = mLayoutInflater.inflate(R.layout.list_item_route, parent, false);
            return new RouteHolder(mView);
        }

        @Override
        public void onBindViewHolder(RouteHolder holder, int position) {
            holder.bindRoute(this.mStopPoints.get(position));
        }

        @Override
        public int getItemCount() {
            return this.mStopPoints.size();
        }
    }
}
