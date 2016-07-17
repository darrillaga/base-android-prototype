package me.darrillaga.prototype.viewer.viewpendingorders.viewmodel;

import me.darrillaga.prototype.commons.model.DeliveryOrder;

public class DeliveryOrderViewModel {

    private DeliveryOrder mDeliveryOrder;

    public DeliveryOrderViewModel(DeliveryOrder deliveryOrder) {
        mDeliveryOrder = deliveryOrder;
    }

    public String getId() {
        return mDeliveryOrder.getId();
    }

    public String getUserName() {
        return "Dami";
    }

    public String getOrderText() {
        return mDeliveryOrder.getOrderText();
    }
}
