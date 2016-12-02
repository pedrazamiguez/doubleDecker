package uk.co.andrespedraza.android.doubledecker.network.request;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import uk.co.andrespedraza.android.doubledecker.network.domain.Prediction;
import uk.co.andrespedraza.android.doubledecker.network.service.TfLEndpoints;

public class ListPredictionsRequest extends RetrofitSpiceRequest<Prediction.List, TfLEndpoints> {

    public static final String CACHE_KEY = ListPredictionsRequest.class.getSimpleName();

    private String lineId;

    public ListPredictionsRequest(String lineId) {
        super(Prediction.List.class, TfLEndpoints.class);
        this.lineId = lineId;
    }

    @Override
    public Prediction.List loadDataFromNetwork() throws Exception {
        return getService().listArrivalPredictionsForStop(lineId);
    }
}
