package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.graphics.Color;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import me.darrillaga.prototype.viewer.childrenguardian.viewmodel.ChildViewModel;

public class ChildrenMarkerDrawer {

    private ChildViewModel mCurrentChildViewModel;
    private Map<ChildViewModel, Marker> mChildViewModelMarkerMap;
    private GoogleMap mGoogleMap;

    public ChildrenMarkerDrawer(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mChildViewModelMarkerMap = new HashMap<>();
    }

    public Marker drawMarkerOrGet(ChildViewModel childViewModel) {
        Marker marker = mChildViewModelMarkerMap.get(childViewModel);

        if (marker == null) {
            LatLng position = new LatLng(childViewModel.getLatitude(), childViewModel.getLongitude());

            float[] hsv = new float[3];

            Color.colorToHSV(childViewModel.getColor(), hsv);

            marker = mGoogleMap.addMarker(
                    new MarkerOptions()
                            .position(position)
                            .title(childViewModel.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(hsv[0]))
            );

            mChildViewModelMarkerMap.put(childViewModel, marker);
        }

        return marker;
    }

    public void onChildSelected(ChildViewModel childViewModel) {
        if (mCurrentChildViewModel != null) {
            mCurrentChildViewModel.setSelected(false);
        }

        mCurrentChildViewModel = childViewModel;
        mCurrentChildViewModel.setSelected(true);

        Marker marker = drawMarkerOrGet(mCurrentChildViewModel);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
    }
}
