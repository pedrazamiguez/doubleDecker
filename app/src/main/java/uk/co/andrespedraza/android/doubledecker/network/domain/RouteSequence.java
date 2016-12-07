package uk.co.andrespedraza.android.doubledecker.network.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteSequence {

    @SerializedName("$type")
    @Expose
    private String type;
    @SerializedName("lineId")
    @Expose
    private String lineId;
    @SerializedName("lineName")
    @Expose
    private String lineName;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("isOutboundOnly")
    @Expose
    private Boolean isOutboundOnly;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("stopPointSequences")
    @Expose
    private List<StopPointSequence> stopPointSequences = null;

    /**
     * @return The type
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
     * @return The isOutboundOnly
     */
    public Boolean getIsOutboundOnly() {
        return isOutboundOnly;
    }

    /**
     * @param isOutboundOnly The isOutboundOnly
     */
    public void setIsOutboundOnly(Boolean isOutboundOnly) {
        this.isOutboundOnly = isOutboundOnly;
    }

    /**
     * @return The mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode The mode
     */
    public void setMode(String mode) {
        this.mode = mode;
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

    /**
     * @return The stopPointSequences
     */
    public List<StopPointSequence> getStopPointSequences() {
        return stopPointSequences;
    }

    /**
     * @param stopPointSequences The stopPointSequences
     */
    public void setStopPointSequences(List<StopPointSequence> stopPointSequences) {
        this.stopPointSequences = stopPointSequences;
    }

}
