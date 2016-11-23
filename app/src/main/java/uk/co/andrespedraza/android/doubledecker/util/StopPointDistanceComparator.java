package uk.co.andrespedraza.android.doubledecker.util;

import android.location.Location;

import java.util.Comparator;

import uk.co.andrespedraza.android.doubledecker.network.domain.StopPoint;

public class StopPointDistanceComparator implements Comparator<StopPoint> {

    private Location mCurrentLocation;

    public StopPointDistanceComparator(Location mCurrentLocation) {
        this.mCurrentLocation = mCurrentLocation;
    }

    @Override
    public int compare(StopPoint o1, StopPoint o2) {

        int result = 0;

        if (null != o1 && null != o2) {
            result = Math.round(o1.getCalculatedDistance(mCurrentLocation) - o2.getCalculatedDistance(mCurrentLocation));
        }

        return result;
    }
}
