package uk.co.andrespedraza.android.doubledecker.network.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import uk.co.andrespedraza.android.doubledecker.network.domain.RouteSequence;
import uk.co.andrespedraza.android.doubledecker.network.domain.enums.LineRouteDirection;
import uk.co.andrespedraza.android.doubledecker.network.service.TfLEndpoints;
import uk.co.andrespedraza.android.doubledecker.util.TfLUtils;

public class ListLineRouteRequest extends RetrofitSpiceRequest<RouteSequence, TfLEndpoints> {

    public static final String CACHE_KEY = ListLineRouteRequest.class.getSimpleName();

    private String lineName;
    private LineRouteDirection direction;

    public ListLineRouteRequest(String lineName, LineRouteDirection direction) {
        super(RouteSequence.class, TfLEndpoints.class);
        this.lineName = lineName;
        this.direction = direction;
    }

    @Override
    public RouteSequence loadDataFromNetwork() throws Exception {
        return getService().findLineRoute(
                this.lineName,
                this.direction.getValue(),
                TfLUtils.TFL_ROUTE_SEQUENCE_SERVICE_TYPES,
                TfLUtils.TFL_ROUTE_SEQUENCE_EXCLUDE_CROWDING
        );
    }
}
