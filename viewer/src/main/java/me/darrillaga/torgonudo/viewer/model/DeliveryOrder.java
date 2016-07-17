package me.darrillaga.torgonudo.viewer.model;

import java.util.Date;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
