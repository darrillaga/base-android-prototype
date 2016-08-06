package me.darrillaga.prototype.viewer.childrenguardian.view;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.tbruyelle.rxpermissions.RxPermissions;

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

import static me.darrillaga.prototype.viewer.childrenguardian.view.ZonesRenderer.CIRCLE_ADDITION;
import static me.darrillaga.prototype.viewer.childrenguardian.view.ZonesRenderer.POLYGON_ADDITION;

@IntentBuilder
public class ChildrenLocationMapActivity extends FragmentActivity
        implements OnMapReadyCallback, EventsHandler, MarketplaceFragment.Interactions {

    private GoogleMap mMap;
    private ActivityChildrenLocationMapBinding mActivityChildrenLocationMapBinding;
    private ChildrenViewModel mChildrenViewModel;

    private BehaviorSubject<GoogleMap> mMapBehaviourSubject = BehaviorSubject.create();

    private CompositeSubscription mCompositeSubscription;

    private ZonesRenderer mZonesRenderer;

    private ChildrenMarkerDrawer mChildrenMarkerDrawer;

    private ChildViewModel mCurrentChildViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        mChildrenMarkerDrawer = new ChildrenMarkerDrawer(mMap);

        mMap.setLocationSource(new MontevideoLocationSource(mMap));

        mZonesRenderer = new ZonesRenderer(this, mMap);

        mMap.setOnMapClickListener(mZonesRenderer::renderShape);

        requestLocationPermissions();

        mMapBehaviourSubject.onNext(mMap);
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
                .subscribe(childViewModel -> mChildrenMarkerDrawer.drawMarkerOrGet(childViewModel));
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

    @Override
    public void onChildSelected(ChildViewModel childViewModel) {
        mCurrentChildViewModel = childViewModel;
        mChildrenMarkerDrawer.onChildSelected(childViewModel);
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
        mZonesRenderer.setRenderType(CIRCLE_ADDITION);
    }

    @Override
    public void onSecureZoneCreatorClick(View view) {
        Toast.makeText(this, R.string.tap_to_create_secure_zone, Toast.LENGTH_LONG).show();
        mZonesRenderer.setRenderType(POLYGON_ADDITION);
    }

    @Override
    public void onMarketPlaceClick(View view) {

        String tag = MarketplaceFragment.class.getName();

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (fragment != null) {
            getSupportFragmentManager().popBackStack(tag, 0);
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(
                        R.id.fragment_container,
                        new MarketplaceFragmentBuilder(mChildrenViewModel.childViewModels, mCurrentChildViewModel).build(),
                        tag
                )
                .addToBackStack(tag)
                .commit();
    }

    @Override
    public void onPurchaseConfirmed() {
        getSupportFragmentManager().popBackStack();
    }
}
