package uk.co.andrespedraza.android.doubledecker.network.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Identifier implements Parcelable {

    @SerializedName("$type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("type")
    @Expose
    private String lineType;

    /**
     * @return The $lineType
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The $lineType
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
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return The lineType
     */
    public String getLineType() {
        return lineType;
    }

    /**
     * @param lineType The lineType
     */
    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.uri);
        dest.writeString(this.lineType);
    }

    public Identifier() {
    }

    protected Identifier(Parcel in) {
        this.type = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.uri = in.readString();
        this.lineType = in.readString();
    }

    public static final Parcelable.Creator<Identifier> CREATOR = new Parcelable.Creator<Identifier>() {
        @Override
        public Identifier createFromParcel(Parcel source) {
            return new Identifier(source);
        }

        @Override
        public Identifier[] newArray(int size) {
            return new Identifier[size];
        }
    };
}