package uk.co.andrespedraza.android.doubledecker.network.service;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import uk.co.andrespedraza.android.doubledecker.util.TfLUtils;

public class TfLService extends RetrofitGsonSpiceService {

    private final static String BASE_URL = "https://api.tfl.gov.uk/";
    private final static String TFL_APP_ID = "28547654";
    private final static String TFL_APP_KEY = "ed2f0fa213c027a570f82797e8348b8c";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(TfLEndpoints.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

    @Override
    protected Converter createConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return TfLUtils.parseDate(json.getAsJsonPrimitive().getAsString());
            }
        });

        return new GsonConverter(gsonBuilder.create());
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {

        return new RestAdapter.Builder()
                .setEndpoint(getServerUrl())
                .setConverter(getConverter())
                .setRequestInterceptor(new AuthenticationInterceptor());
    }

    private class AuthenticationInterceptor implements RequestInterceptor {

        @Override
        public void intercept(RequestInterceptor.RequestFacade request) {
            request.addEncodedQueryParam("app_id", TFL_APP_ID);
            request.addEncodedQueryParam("app_key", TFL_APP_KEY);
        }
    }

}
