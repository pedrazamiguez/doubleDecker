package uk.co.andrespedraza.android.doubledecker.network.domain.enums;

public enum LineRouteDirection {

    INBOUND("inbound"),
    OUTBOUND("outbound");

    private String value;

    private LineRouteDirection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
