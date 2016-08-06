package me.darrillaga.prototype.viewer.childrenguardian.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import me.darrillaga.prototype.viewer.BR;

public class MarketplaceItemViewModel extends BaseObservable {

    private String imagePath;
    private String name;
    private int quantity;
    private int price;
    private String category;
    private String description;

    private boolean isChildrenListVisible;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return String.valueOf(quantity);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return String.valueOf(price);
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void toggleShowChildren() {
        isChildrenListVisible = !isChildrenListVisible;
        notifyPropertyChanged(BR.childrenListVisible);
    }

    @Bindable
    public boolean isChildrenListVisible() {
        return isChildrenListVisible;
    }
}
