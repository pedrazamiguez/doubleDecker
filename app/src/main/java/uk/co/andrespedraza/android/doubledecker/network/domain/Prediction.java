package uk.co.andrespedraza.android.doubledecker.network.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

import uk.co.andrespedraza.android.doubledecker.util.TfLUtils;

public class Prediction implements Comparable<Prediction> {

    @SerializedName("$type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("operationType")
    @Expose
    private Integer operationType;
    @SerializedName("vehicleId")
    @Expose
    private String vehicleId;
    @SerializedName("naptanId")
    @Expose
    private String naptanId;
    @SerializedName("stationName")
    @Expose
    private String stationName;
    @SerializedName("lineId")
    @Expose
    private String lineId;
    @SerializedName("lineName")
    @Expose
    private String lineName;
    @SerializedName("platformName")
    @Expose
    private String platformName;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("bearing")
    @Expose
    private String bearing;
    @SerializedName("destinationNaptanId")
    @Expose
    private String destinationNaptanId;
    @SerializedName("destinationName")
    @Expose
    private String destinationName;
    @SerializedName("timestamp")
    @Expose
    private Date timestamp;
    @SerializedName("timeToStation")
    @Expose
    private Integer timeToStation;
    @SerializedName("currentLocation")
    @Expose
    private String currentLocation;
    @SerializedName("towards")
    @Expose
    private String towards;
    @SerializedName("expectedArrival")
    @Expose
    private Date expectedArrival;
    @SerializedName("timeToLive")
    @Expose
    private Date timeToLive;
    @SerializedName("modeName")
    @Expose
    private String modeName;
    @SerializedName("timing")
    @Expose
    private PredictionTiming timing;

    private double minValue;

    /**
     * @return The $type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The operationType
     */
    public Integer getOperationType() {
        return operationType;
    }

    /**
     * @param operationType The operationType
     */
    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    /**
     * @return The vehicleId
     */
    public String getVehicleId() {
        return vehicleId;
    }

    /**
     * @param vehicleId The vehicleId
     */
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * @return The naptanId
     */
    public String getNaptanId() {
        return naptanId;
    }

    /**
     * @param naptanId The naptanId
     */
    public void setNaptanId(String naptanId) {
        this.naptanId = naptanId;
    }

    /**
     * @return The stationName
     */
    public String getStationName() {
        return stationName;
    }

    /**
     * @param stationName The stationName
     */
    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    /**
     * @return The lineId
     */
    public String getLineId() {
        return lineId;
    }

    /**
     * @param lineId The lineId
     */
    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    /**
     * @return The lineName
     */
    public String getLineName() {
        return lineName;
    }

    /**
     * @param lineName The lineName
     */
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    /**
     * @return The platformName
     */
    public String getPlatformName() {
        return platformName;
    }

    /**
     * @param platformName The platformName
     */
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    /**
     * @return The direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * @param direction The direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * @return The bearing
     */
    public String getBearing() {
        return bearing;
    }

    /**
     * @param bearing The bearing
     */
    public void setBearing(String bearing) {
        this.bearing = bearing;
    }

    /**
     * @return The destinationNaptanId
     */
    public String getDestinationNaptanId() {
        return destinationNaptanId;
    }

    /**
     * @param destinationNaptanId The destinationNaptanId
     */
    public void setDestinationNaptanId(String destinationNaptanId) {
        this.destinationNaptanId = destinationNaptanId;
    }

    /**
     * @return The destinationName
     */
    public String getDestinationName() {
        return destinationName;
    }

    /**
     * @param destinationName The destinationName
     */
    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    /**
     * @return The timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp The timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return The timeToStation
     */
    public Integer getTimeToStation() {
        return timeToStation;
    }

    /**
     * @param timeToStation The timeToStation
     */
    public void setTimeToStation(Integer timeToStation) {
        this.timeToStation = timeToStation;
    }

    /**
     * @return The currentLocation
     */
    public String getCurrentLocation() {
        return currentLocation;
    }

    /**
     * @param currentLocation The currentLocation
     */
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    /**
     * @return The towards
     */
    public String getTowards() {
        return towards;
    }

    /**
     * @param towards The towards
     */
    public void setTowards(String towards) {
        this.towards = towards;
    }

    /**
     * @return The expectedArrival
     */
    public Date getExpectedArrival() {
        return expectedArrival;
    }

    /**
     * @param expectedArrival The expectedArrival
     */
    public void setExpectedArrival(Date expectedArrival) {
        this.expectedArrival = expectedArrival;
    }

    /**
     * @return The timeToLive
     */
    public Date getTimeToLive() {
        return timeToLive;
    }

    /**
     * @param timeToLive The timeToLive
     */
    public void setTimeToLive(Date timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * @return The modeName
     */
    public String getModeName() {
        return modeName;
    }

    /**
     * @param modeName The modeName
     */
    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    /**
     * @return The timing
     */
    public PredictionTiming getTiming() {
        return timing;
    }

    /**
     * @param timing The timing
     */
    public void setTiming(PredictionTiming timing) {
        this.timing = timing;
    }

    private void calculateExpectedTime() {
        if (null != timestamp && null != expectedArrival) {
            minValue = TfLUtils.calculateTimeValue(timestamp, expectedArrival);
        }
    }

    public static void calculateExpectedTimes(Prediction.List list) {
        if (null != list) {
            for (Prediction p : list) {
                p.calculateExpectedTime();
            }
        }
    }

    @Override
    public int compareTo(Prediction o) {

        int result = 0;

        if (null != timestamp && null != expectedArrival && null != o && null != o.getTimestamp() && null != o.getExpectedArrival()) {
            int oMinValue1 = (int) Math.round(100 * minValue);
            int oMinValue2 = (int) Math.round(100 * o.getMinValue());
            result = oMinValue1 - oMinValue2;
            if (0 == result) {
                result = lineName.compareToIgnoreCase(o.getLineName());
            }
        }

        return result;
    }

    public String getMinValueString() {
        String result;
        if (99d <= minValue) {
            result = "Prediction not available";
        } else {
            int minutes = (int) Math.round(minValue);
            if (0 == minutes) {
                result = "due";
            } else if (1 == minutes) {
                result = "1 min";
            } else {
                result = String.format("%s min", minutes);
            }
        }
        return result;
    }

    public double getMinValue() {
        return minValue;
    }

    public static class List extends ArrayList<Prediction> {

    }
}