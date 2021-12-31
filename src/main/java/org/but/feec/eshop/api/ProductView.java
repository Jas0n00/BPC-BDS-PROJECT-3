package org.but.feec.eshop.api;

import javafx.beans.property.*;

public class ProductView {

    private StringProperty brand = new SimpleStringProperty();
    private StringProperty model = new SimpleStringProperty();
    private StringProperty version = new SimpleStringProperty();
    private StringProperty color = new SimpleStringProperty();
    private StringProperty type = new SimpleStringProperty();
    private StringProperty price = new SimpleStringProperty();
    private StringProperty stock = new SimpleStringProperty();


    public String getBrand() {
        return brandProperty().get();
    }

    public void setBrand(String brand) {
        this.brandProperty().setValue(brand);
    }

    public String getModel() {
        return modelProperty().get();
    }

    public void setModel(String model) {
        this.modelProperty().set(model);
    }

    public String getVersion() {
        return versionProperty().get();
    }

    public void setVersion(String version) {
        this.versionProperty().set(version);
    }

    public String getColor() {
        return colorProperty().get();
    }

    public void setColor(String color) {
        this.colorProperty().set(color);
    }

    public String getType() {
        return typeProperty().get();
    }

    public void setType(String type) {
        this.typeProperty().set(type);
    }

    public String getPrice() {
        return priceProperty().get();
    }

    public void setPrice(String price) {
        this.priceProperty().set(price);
    }

    public String getStock() {
        return stockProperty().get();
    }

    public void setStock(String stock) {
        this.stockProperty().set(stock);
    }




    public StringProperty brandProperty() {
        return brand;
    }

    public StringProperty modelProperty() {
        return model;
    }

    public StringProperty versionProperty() {
        return version;
    }

    public StringProperty colorProperty() {
        return color;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public StringProperty priceProperty() {
        return price;
    }

    public StringProperty stockProperty() {
        return stock;
    }
}
