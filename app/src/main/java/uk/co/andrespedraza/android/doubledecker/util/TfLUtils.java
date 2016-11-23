package uk.co.andrespedraza.android.doubledecker.util;

import android.location.Location;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.andrespedraza.android.doubledecker.network.domain.StopPoint;

public class TfLUtils {

    public static final String TFL_BUS_STOP_TYPES = "NaptanBusCoachStation,NaptanBusWayPoint,NaptanPrivateBusCoachTram,NaptanPublicBusCoachTram";
    public static final String TFL_BUS_STOP_RADIUS_LOOKUP = "300"; // in metres
    public static final String TFL_BUS_STOP_USE_HIERARCHY = "False";
    public static final String TFL_BUS_STOP_MODE = "bus";
    public static final String TFL_BUS_STOP_RETURN_LINES = "True";
    private static final String TFL_DATE_FORMAT_01 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String TFL_DATE_FORMAT_02 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String TFL_DATE_FORMAT_03 = "yyyy-MM-dd'T'HH:mm:ss";

    public static Date parseDate(String dateInfo) {
        Date date;

        try {
            date = new SimpleDateFormat(TFL_DATE_FORMAT_01).parse(dateInfo);
        } catch (ParseException e) {
            try {
                date = new SimpleDateFormat(TFL_DATE_FORMAT_02).parse(dateInfo);
            } catch (ParseException f) {
                try {
                    date = new SimpleDateFormat(TFL_DATE_FORMAT_03).parse(dateInfo);
                } catch (ParseException g) {
                    date = new Date();
                }
            }
        }

        return date;
    }

    public static float calculateDistance(double latitude, double longitude, Location currentLocation) {
        Location targetLocation = new Location("");
        targetLocation.setLatitude(latitude);
        targetLocation.setLongitude(longitude);
        return targetLocation.distanceTo(currentLocation);
    }

    public static List<StopPoint> removeStopPointsWithoutLines(List<StopPoint> lstStopPoints) {

        List<StopPoint> lstFinalStopPoints = new ArrayList<>();

        if (null != lstStopPoints) {
            for (StopPoint sp : lstStopPoints) {
                if (null != sp.getLines() && !sp.getLines().isEmpty()) {
                    if (null == sp.getStopLetter() || (null != sp.getStopLetter() && !sp.getStopLetter().isEmpty() && !sp.getStopLetter().contains("-"))) {
                        lstFinalStopPoints.add(sp);
                    }
                }
            }
        }

        return lstFinalStopPoints;
    }

}
