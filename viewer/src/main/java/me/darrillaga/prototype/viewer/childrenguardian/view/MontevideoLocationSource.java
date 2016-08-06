package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MontevideoLocationSource implements LocationSource {

    private static final String LOCATION_NAME = "Montevideo";
    private static final float DEFAULT_ZOOM = 12;
    private static final double MONTEVIDEO_LATITUDE = -34.90111;
    private static final double MONTEVIDEO_LONGITUDE = -56.16453;
    private static final float ACCURACY = 100;

    private GoogleMap mGoogleMap;

    public MontevideoLocationSource(GoogleMap googleMap) {
        mGoogleMap = googleMap;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        Location location = new Location(LOCATION_NAME);

        LatLng montevideo = new LatLng(MONTEVIDEO_LATITUDE, MONTEVIDEO_LONGITUDE);

        location.setLatitude(montevideo.latitude);
        location.setLongitude(montevideo.longitude);
        location.setAccuracy(ACCURACY);

        onLocationChangedListener.onLocationChanged(location);

        mGoogleMap.moveCamera(
                CameraUpdateFactory.newCameraPosition(
                        CameraPosition.builder()
                                .zoom(DEFAULT_ZOOM)
                                .target(montevideo)
                                .build()
                )
        );
    }

    @Override
    public void deactivate() {

    }
}
