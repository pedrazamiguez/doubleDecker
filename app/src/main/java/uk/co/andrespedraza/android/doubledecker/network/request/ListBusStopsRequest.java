package uk.co.andrespedraza.android.doubledecker.network.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import uk.co.andrespedraza.android.doubledecker.network.domain.StopPointsResponse;
import uk.co.andrespedraza.android.doubledecker.network.service.TfLEndpoints;
import uk.co.andrespedraza.android.doubledecker.util.TfLUtils;

public class ListBusStopsRequest extends RetrofitSpiceRequest<StopPointsResponse, TfLEndpoints> {

    public static final String CACHE_KEY = ListBusStopsRequest.class.getSimpleName();

    private double lat;
    private double lon;

    public ListBusStopsRequest(double lat, double lon) {
        super(StopPointsResponse.class, TfLEndpoints.class);
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public StopPointsResponse loadDataFromNetwork() throws Exception {
        return getService().listBusStops(
                String.valueOf(this.lat),
                String.valueOf(this.lon),
                TfLUtils.TFL_BUS_STOP_TYPES,
                TfLUtils.TFL_BUS_STOP_RADIUS_LOOKUP,
                TfLUtils.TFL_BUS_STOP_USE_HIERARCHY,
                TfLUtils.TFL_BUS_STOP_MODE,
                TfLUtils.TFL_BUS_STOP_RETURN_LINES
        );
    }
}
