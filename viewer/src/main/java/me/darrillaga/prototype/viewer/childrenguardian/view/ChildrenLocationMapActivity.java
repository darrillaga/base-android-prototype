package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;
import java.util.Map;

import me.darrillaga.prototype.commons.util.Subscribers;
import me.darrillaga.prototype.viewer.R;
import me.darrillaga.prototype.viewer.childrenguardian.viewmodel.ChildViewModel;
import me.darrillaga.prototype.viewer.childrenguardian.viewmodel.ChildrenViewModel;
import me.darrillaga.prototype.viewer.databinding.ActivityChildrenLocationMapBinding;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;
import se.emilsjolander.intentbuilder.IntentBuilder;

@IntentBuilder
public class ChildrenLocationMapActivity extends FragmentActivity
        implements OnMapReadyCallback, EventsHandler {

    private static final int NO_ADDITION = -1;
    private static final int CIRCLE_ADDITION = 0;
    private static final int POLYGON_ADDITION = 1;

    private GoogleMap mMap;
    private ActivityChildrenLocationMapBinding mActivityChildrenLocationMapBinding;
    private ChildViewModel mCurrentChildViewModel;
    private ChildrenViewModel mChildrenViewModel;

    private BehaviorSubject<GoogleMap> mMapBehaviourSubject = BehaviorSubject.create();

    private int nextAddition;

    private Map<ChildViewModel, Marker> mChildViewModelMarkerMap;

    private CompositeSubscription mCompositeSubscription;

    private LocationSource mMontevideoLocationSource = new LocationSource() {
        @Override
        public void activate(OnLocationChangedListener onLocationChangedListener) {
            Location location = new Location("Montevideo");

            LatLng montevideo = new LatLng(-34.90111, -56.16453);

            location.setLatitude(montevideo.latitude);
            location.setLongitude(montevideo.longitude);
            location.setAccuracy(100);

            onLocationChangedListener.onLocationChanged(location);

            mMap.moveCamera(
                    CameraUpdateFactory.newCameraPosition(
                            CameraPosition.builder()
                                    .zoom(12)
                                    .target(montevideo)
                                    .build()
                    )
            );
        }

        @Override
        public void deactivate() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mChildViewModelMarkerMap = new HashMap<>();

        mChildrenViewModel = new ChildrenViewModel(getResources().getIntArray(R.array.material_colors));

        mActivityChildrenLocationMapBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_children_location_map);

        mActivityChildrenLocationMapBinding.setEventsHandler(this);
        mActivityChildrenLocationMapBinding.setChildrenViewModel(mChildrenViewModel);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMapBehaviourSubject.onNext(mMap);

        mMap.setLocationSource(mMontevideoLocationSource);

        mMap.setOnMapClickListener(this::renderShape);

        requestLocationPermissions();
    }

    private void requestLocationPermissions() {
        RxPermissions.getInstance(this).requestEach(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ).subscribe(Subscribers.subscribeWithSubscription((subscription, permission) -> {
            if (permission.granted) {
                //noinspection MissingPermission
                mMap.setMyLocationEnabled(true);
                subscription.unsubscribe();
            }
        }));
    }

    private Subscription subscribeToFetchChildrenWhenMapReady() {
        return Observable.combineLatest(
                mMapBehaviourSubject,
                mChildrenViewModel.fetchChildren()
                        .subscribeOn(Schedulers.io()),
                (googleMap, childViewModel) -> childViewModel
        )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::drawMarkerOrGet);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(
                subscribeToFetchChildrenWhenMapReady()
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCompositeSubscription.unsubscribe();
    }

    private Marker drawMarkerOrGet(ChildViewModel childViewModel) {
        Marker marker = mChildViewModelMarkerMap.get(childViewModel);

        if (marker == null) {
            LatLng position = new LatLng(childViewModel.getLatitude(), childViewModel.getLongitude());

            float[] hsv = new float[3];

            Color.colorToHSV(childViewModel.getColor(), hsv);

            marker = mMap.addMarker(
                    new MarkerOptions()
                            .position(position)
                            .title(childViewModel.getName())
                            .icon(BitmapDescriptorFactory.defaultMarker(hsv[0]))
            );

            mChildViewModelMarkerMap.put(childViewModel, marker);
        }

        return marker;
    }

    @Override
    public void onChildSelected(ChildViewModel childViewModel) {
        if (mCurrentChildViewModel != null) {
            mCurrentChildViewModel.setSelected(false);
        }

        mCurrentChildViewModel = childViewModel;
        mCurrentChildViewModel.setSelected(true);

        Marker marker = drawMarkerOrGet(mCurrentChildViewModel);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
    }

    @Override
    public void onToolsToggleClick(View view) {
        int visibility;

        if (mActivityChildrenLocationMapBinding.toolsWrapper.getVisibility() == View.VISIBLE) {
            visibility = View.GONE;
        } else {
            visibility = View.VISIBLE;
        }

        mActivityChildrenLocationMapBinding.toolsWrapper.setVisibility(visibility);
    }

    @Override
    public void onDangerZoneCreatorClick(View view) {
        Toast.makeText(this, R.string.tap_to_create_danger_zone, Toast.LENGTH_LONG).show();
        nextAddition = CIRCLE_ADDITION;
    }

    @Override
    public void onSecureZoneCreatorClick(View view) {
        Toast.makeText(this, R.string.tap_to_create_secure_zone, Toast.LENGTH_LONG).show();
        nextAddition = POLYGON_ADDITION;
    }

    private void renderShape(LatLng latLng) {
        switch (nextAddition) {
            case CIRCLE_ADDITION:
                renderCircle(latLng);
                break;
            case POLYGON_ADDITION:
                renderPolygon(latLng);
                break;
        }
        nextAddition = NO_ADDITION;
    }

    private void renderPolygon(LatLng latLng) {
        int blueColor = ContextCompat.getColor(this, R.color.blue_300);
        mMap.addPolygon(
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
        int redColor = ContextCompat.getColor(this, R.color.red_500);
        mMap.addCircle(
                new CircleOptions()
                        .fillColor(ColorUtils.applyAlpha(100, redColor))
                        .strokeWidth(0)
                        .radius(500)
                        .center(latLng)
        );
    }
}
