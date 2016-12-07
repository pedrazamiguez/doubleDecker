package uk.co.andrespedraza.android.doubledecker.network.domain;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import uk.co.andrespedraza.android.doubledecker.util.TfLUtils;

public class StopPoint implements Parcelable {

    @SerializedName("$type")
    @Expose
    private String type;
    @SerializedName("naptanId")
    @Expose
    private String naptanId;
    @SerializedName("indicator")
    @Expose
    private String indicator;
    @SerializedName("stopLetter")
    @Expose
    private String stopLetter;
    @SerializedName("icsCode")
    @Expose
    private String icsCode;
    @SerializedName("stopType")
    @Expose
    private String stopType;
    @SerializedName("stationNaptan")
    @Expose
    private String stationNaptan;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("commonName")
    @Expose
    private String commonName;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("placeType")
    @Expose
    private String placeType;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("lines")
    @Expose
    private List<Identifier> lines;

    private float mCalculatedDistance;

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
     * @return The indicator
     */
    public String getIndicator() {
        return indicator;
    }

    /**
     * @param indicator The indicator
     */
    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    /**
     * @return The stopLetter
     */
    public String getStopLetter() {
        return stopLetter;
    }

    /**
     * @param stopLetter The stopLetter
     */
    public void setStopLetter(String stopLetter) {
        this.stopLetter = stopLetter;
    }

    /**
     * @return The icsCode
     */
    public String getIcsCode() {
        return icsCode;
    }

    /**
     * @param icsCode The icsCode
     */
    public void setIcsCode(String icsCode) {
        this.icsCode = icsCode;
    }

    /**
     * @return The stopType
     */
    public String getStopType() {
        return stopType;
    }

    /**
     * @param stopType The stopType
     */
    public void setStopType(String stopType) {
        this.stopType = stopType;
    }

    /**
     * @return The stationNaptan
     */
    public String getStationNaptan() {
        return stationNaptan;
    }

    /**
     * @param stationNaptan The stationNaptan
     */
    public void setStationNaptan(String stationNaptan) {
        this.stationNaptan = stationNaptan;
    }

    /**
     * @return The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
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
     * @return The commonName
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     * @param commonName The commonName
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /**
     * @return The distance
     */
    public Double getDistance() {
        return distance;
    }

    /**
     * @param distance The distance
     */
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    /**
     * @return The placeType
     */
    public String getPlaceType() {
        return placeType;
    }

    /**
     * @param placeType The placeType
     */
    public void setPlaceType(String placeType) {
        this.placeType = placeType;
    }

    /**
     * @return The lat
     */
    public Double getLat() {
        return lat;
    }

    /**
     * @param lat The lat
     */
    public void setLat(Double lat) {
        this.lat = lat;
    }

    /**
     * @return The lon
     */
    public Double getLon() {
        return lon;
    }

    /**
     * @param lon The lon
     */
    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Identifier> getLines() {
        return lines;
    }

    public void setLines(List<Identifier> lines) {
        this.lines = lines;
    }

    public String generateLinesList() {
        String linesStr = "";

        if (null != lines && !lines.isEmpty()) {
            for (Identifier line : lines) {
                if (null != line.getName() && !line.getName().trim().isEmpty()) {
                    linesStr += line.getName().trim() + ", ";
                }
            }
            if (linesStr.length() >= 2) {
                linesStr = linesStr.substring(0, linesStr.length() - 2);
            }
        }

        return linesStr;
    }

    public String getRoundedDistance() {
        String roundedDistance = "";

        if (null != distance) {
            roundedDistance = String.format("%s m", Math.round(distance));
        }

        return roundedDistance;
    }

    public float getCalculatedDistance(Location currentLocation) {
        mCalculatedDistance = TfLUtils.calculateDistance(getLat(), getLon(), currentLocation);
        return mCalculatedDistance;
    }

    public String getRoundedCalculatedDistance() {
        return String.format("%s m", Math.round(mCalculatedDistance));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.naptanId);
        dest.writeString(this.indicator);
        dest.writeString(this.stopLetter);
        dest.writeString(this.icsCode);
        dest.writeString(this.stopType);
        dest.writeString(this.stationNaptan);
        dest.writeValue(this.status);
        dest.writeString(this.id);
        dest.writeString(this.commonName);
        dest.writeValue(this.distance);
        dest.writeString(this.placeType);
        dest.writeValue(this.lat);
        dest.writeValue(this.lon);
        dest.writeList(this.lines);
        dest.writeFloat(this.mCalculatedDistance);
    }

    public StopPoint() {
    }

    protected StopPoint(Parcel in) {
        this.type = in.readString();
        this.naptanId = in.readString();
        this.indicator = in.readString();
        this.stopLetter = in.readString();
        this.icsCode = in.readString();
        this.stopType = in.readString();
        this.stationNaptan = in.readString();
        this.status = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.id = in.readString();
        this.commonName = in.readString();
        this.distance = (Double) in.readValue(Double.class.getClassLoader());
        this.placeType = in.readString();
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.lon = (Double) in.readValue(Double.class.getClassLoader());
        this.lines = new ArrayList<Identifier>();
        in.readList(this.lines, Identifier.class.getClassLoader());
        this.mCalculatedDistance = in.readFloat();
    }

    public static final Parcelable.Creator<StopPoint> CREATOR = new Parcelable.Creator<StopPoint>() {
        @Override
        public StopPoint createFromParcel(Parcel source) {
            return new StopPoint(source);
        }

        @Override
        public StopPoint[] newArray(int size) {
            return new StopPoint[size];
        }
    };
}