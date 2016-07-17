package me.darrillaga.prototype.viewer.viewpendingorders.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.google.firebase.database.DatabaseReference;
import com.kelvinapps.rxfirebase.RxFirebaseChildEvent;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import java8.util.stream.StreamSupport;
import me.darrillaga.prototype.commons.model.DeliveryOrder;
import rx.Observable;

public class ViewPendingOrdersViewModel {

    public final ObservableList<DeliveryOrderViewModel> deliveryOrders;
    public final ObservableBoolean loadingOrders;

    private DatabaseReference mDatabaseReference;

    public ViewPendingOrdersViewModel(DatabaseReference databaseReference) {
        mDatabaseReference = databaseReference;
        deliveryOrders = new ObservableArrayList<>();
        loadingOrders = new ObservableBoolean();
    }

    public Observable<RxFirebaseChildEvent<DeliveryOrder>> fetchOrders() {
        Observable<RxFirebaseChildEvent<DeliveryOrder>> deliveryOrdersObservable = RxFirebaseDatabase.observeChildrenEvents(
                mDatabaseReference.getRef()
                        .child("deliveryOrders")
                        .orderByChild("status")
                        .equalTo(DeliveryOrder.Status.WAITING.value()),
                DeliveryOrder.class
        ).doOnNext(this::refreshChild);

        return deliveryOrdersObservable;
    }

    private void refreshChild(RxFirebaseChildEvent<DeliveryOrder> deliveryOrderRxFirebaseChildEvent) {
        if (deliveryOrderRxFirebaseChildEvent.getEventType() == RxFirebaseChildEvent.EventType.ADDED) {
            addDeliveryOrder(deliveryOrderRxFirebaseChildEvent.getValue());
        } else if (deliveryOrderRxFirebaseChildEvent.getEventType() == RxFirebaseChildEvent.EventType.REMOVED) {
            removeDeliveryOrder(deliveryOrderRxFirebaseChildEvent.getValue());
        }
    }

    private void addDeliveryOrder(DeliveryOrder deliveryOrder) {
        deliveryOrders.add(new DeliveryOrderViewModel(deliveryOrder));
    }

    private void removeDeliveryOrder(DeliveryOrder deliveryOrder) {
        DeliveryOrderViewModel foundDeliveryOrderViewModel = StreamSupport.stream(deliveryOrders)
                .filter(deliveryOrderViewModel -> deliveryOrder.getId().equals(
                        deliveryOrderViewModel.getId()
                ))
                .findFirst().orElseGet(null);

        if (foundDeliveryOrderViewModel != null) {
            deliveryOrders.remove(foundDeliveryOrderViewModel);
        }
    }
}
