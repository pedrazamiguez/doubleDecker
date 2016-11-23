package uk.co.andrespedraza.android.doubledecker.network.service;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import uk.co.andrespedraza.android.doubledecker.network.domain.Prediction;
import uk.co.andrespedraza.android.doubledecker.network.domain.StopPointsResponse;

public interface TfLEndpoints {

    /**
     * Gets the list of arrival predictions for the given stop point id.
     *
     * @param id A StopPoint id (station naptan code e.g. 940GZZLUASL, you can use /StopPoint/Search/{query} endpoint to find a stop point id from a station name)
     * @return A list of all the predictions for each line and platform.
     */
    @GET("/StopPoint/{id}/Arrivals")
    Prediction.List listArrivalPredictionsForStop(@Path("id") String id);

    /**
     * Gets a list of StopPoints within by the specified criteria.
     *
     * @param lat                   The latitude.
     * @param lon                   The longitude.
     * @param stopTypes             a list of stopTypes that should be returned (a list of valid stop types can be obtained from the StopPoint/meta/stoptypes endpoint).
     * @param radius                Optional. the radius of the bounding circle in metres (default : 200).
     * @param useStopPointHierarchy Optional. Re-arrange the output into a parent/child hierarchy (default : True).
     * @param modes                 Optional. the list of modes to search (comma separated mode names e.g. tube,dlr).
     * @param returnLines           Optional. true to return the lines that each stop point serves as a nested resource (default : True)
     * @return An IEnumerable of StopPoint, the centre of the locus and distances of each place from the centre.
     */
    @GET("/StopPoint")
    StopPointsResponse listBusStops(@Query("lat") String lat,
                                    @Query("lon") String lon,
                                    @Query("stopTypes") String stopTypes,
                                    @Query("radius") String radius,
                                    @Query("useStopPointHierarchy") String useStopPointHierarchy,
                                    @Query("modes") String modes,
                                    @Query("returnLines") String returnLines);

}
