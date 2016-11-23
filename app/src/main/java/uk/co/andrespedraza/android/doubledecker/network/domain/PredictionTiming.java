package uk.co.andrespedraza.android.doubledecker.network.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PredictionTiming {

    @SerializedName("$type")
    @Expose
    private String type;
    @SerializedName("countdownServerAdjustment")
    @Expose
    private String countdownServerAdjustment;
    @SerializedName("source")
    @Expose
    private Date source;
    @SerializedName("insert")
    @Expose
    private Date insert;
    @SerializedName("read")
    @Expose
    private Date read;
    @SerializedName("sent")
    @Expose
    private Date sent;
    @SerializedName("received")
    @Expose
    private Date received;

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
     * @return The countdownServerAdjustment
     */
    public String getCountdownServerAdjustment() {
        return countdownServerAdjustment;
    }

    /**
     * @param countdownServerAdjustment The countdownServerAdjustment
     */
    public void setCountdownServerAdjustment(String countdownServerAdjustment) {
        this.countdownServerAdjustment = countdownServerAdjustment;
    }

    /**
     * @return The source
     */
    public Date getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(Date source) {
        this.source = source;
    }

    /**
     * @return The insert
     */
    public Date getInsert() {
        return insert;
    }

    /**
     * @param insert The insert
     */
    public void setInsert(Date insert) {
        this.insert = insert;
    }

    /**
     * @return The read
     */
    public Date getRead() {
        return read;
    }

    /**
     * @param read The read
     */
    public void setRead(Date read) {
        this.read = read;
    }

    /**
     * @return The sent
     */
    public Date getSent() {
        return sent;
    }

    /**
     * @param sent The sent
     */
    public void setSent(Date sent) {
        this.sent = sent;
    }

    /**
     * @return The received
     */
    public Date getReceived() {
        return received;
    }

    /**
     * @param received The received
     */
    public void setReceived(Date received) {
        this.received = received;
    }
}