package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import me.darrillaga.prototype.viewer.R;

public class ZonesRenderer {

    public static final int NO_ADDITION = -1;
    public static final int CIRCLE_ADDITION = 0;
    public static final int POLYGON_ADDITION = 1;

    private int mNextAddition = NO_ADDITION;

    private GoogleMap mGoogleMap;
    private Context mContext;

    public ZonesRenderer(Context context, GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mContext = context;
    }

    public void setRenderType(int renderType) {
        mNextAddition = renderType;
    }

    public void renderShape(LatLng latLng) {
        switch (mNextAddition) {
            case CIRCLE_ADDITION:
                renderCircle(latLng);
                break;
            case POLYGON_ADDITION:
                renderPolygon(latLng);
                break;
        }
        mNextAddition = NO_ADDITION;
    }

    private void renderPolygon(LatLng latLng) {
        int blueColor = ContextCompat.getColor(mContext, R.color.blue_300);
        mGoogleMap.addPolygon(
                new PolygonOptions()
                        .fillColor(ColorUtils.applyAlpha(100, blueColor))
                        .geodesic(true)
                        .strokeWidth(0)
                        .add(
                                new LatLng(latLng.latitude + 0.004, latLng.longitude + 0.004),
                                new LatLng(latLng.latitude, latLng.longitude + 0.01),
                                new LatLng(latLng.latitude - 0.004, latLng.longitude + 0.004),
                                new LatLng(latLng.latitude - 0.003, latLng.longitude - 0.003),
                                new LatLng(latLng.latitude + 0.003, latLng.longitude - 0.003)
                        )
        );
    }

    private void renderCircle(LatLng latLng) {
        int redColor = ContextCompat.getColor(mContext, R.color.red_500);
        mGoogleMap.addCircle(
                new CircleOptions()
                        .fillColor(ColorUtils.applyAlpha(100, redColor))
                        .strokeWidth(0)
                        .radius(500)
                        .center(latLng)
        );
    }
}
