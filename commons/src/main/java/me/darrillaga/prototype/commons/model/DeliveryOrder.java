package me.darrillaga.prototype.commons.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.Date;

import java8.util.stream.RefStreams;

public class DeliveryOrder {

    public enum Status {
        WAITING("waiting"), DELIVERING("delivering"), DELIVERED("delivered"), CANCELED("canceled");

        private String value;

        Status(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }

        public static Status findValue(String value) {
            if (value == null) {
                return null;
            }

            return RefStreams.of(Status.values())
                    .filter(value::equals)
                    .findFirst().orElse(null);
        }
    }

    private String id;
    private String orderText;
    private Double price;
    private Status status;
    private Date createdAt;
    private Date lastStatusUpdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderText() {
        return orderText;
    }

    public void setOrderText(String orderText) {
        this.orderText = orderText;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Exclude
    public Status getStatus() {
        return status;
    }

    @Exclude
    public void setStatus(Status status) {
        this.status = status;
    }

    @PropertyName("status")
    public String getStatusString() {
        return status != null ? status.value() : null;
    }

    @PropertyName("status")
    public void setStatusString(String status) {
        setStatus(status != null ? Status.findValue(status) : null);
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastStatusUpdate() {
        return lastStatusUpdate;
    }

    public void setLastStatusUpdate(Date lastStatusUpdate) {
        this.lastStatusUpdate = lastStatusUpdate;
    }
}
