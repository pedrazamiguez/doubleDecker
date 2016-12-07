package uk.co.andrespedraza.android.doubledecker.network.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StopPointSequence {

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
    @SerializedName("branchId")
    @Expose
    private Integer branchId;
    @SerializedName("stopPoint")
    @Expose
    private List<StopPoint> stopPoint = null;
    @SerializedName("serviceType")
    @Expose
    private String serviceType;

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
     * @return The branchId
     */
    public Integer getBranchId() {
        return branchId;
    }

    /**
     * @param branchId The branchId
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    /**
     * @return The stopPoint
     */
    public List<StopPoint> getStopPoint() {
        return stopPoint;
    }

    /**
     * @param stopPoint The stopPoint
     */
    public void setStopPoint(List<StopPoint> stopPoint) {
        this.stopPoint = stopPoint;
    }

    /**
     * @return The serviceType
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * @param serviceType The serviceType
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

}
