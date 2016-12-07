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

    public static final String TFL_ROUTE_SEQUENCE_SERVICE_TYPES = "regular,night";
    public static final String TFL_ROUTE_SEQUENCE_EXCLUDE_CROWDING = "True";


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

    public static String calculateTimeInMinutes(Date from, Date to) {

        String result;

        if (null != from && null != to) {
            long minutes = Math.abs(from.getTime() - to.getTime()) / (60 * 1000);

            if (0 == minutes) {
                result = "due";
            } else if (1 == minutes) {
                result = "1 min";
            } else {
                result = String.format("%s mins", minutes);
            }

        } else {
            result = "Prediction not available";
        }

        return result;
    }

    public static double calculateTimeValue(Date from, Date to) {

        double result = 99d;

        if (null != from && null != to) {
            result = Math.abs(from.getTime() - to.getTime()) / (60d * 1000);
        }

        if (0d == result) {
            int i;
        }

        return result;
    }

}
