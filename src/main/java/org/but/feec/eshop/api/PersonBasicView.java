package org.but.feec.eshop.api;

import javafx.beans.property.*;

public class PersonBasicView {
    private LongProperty id = new SimpleLongProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private StringProperty first_name = new SimpleStringProperty();
    private StringProperty last_name = new SimpleStringProperty();
    private StringProperty phone_number = new SimpleStringProperty();

    public Long getId() {
        return idProperty().get();
    }

    public void setId(long id) {
        this.idProperty().setValue(id);
    }

    public String getCity() {
        return cityProperty().get();
    }

    public void setCity(String city) {
        this.cityProperty().setValue(city);
    }

    public String getEmail() {
        return emailProperty().get();
    }

    public void setEmail(String email) {
        this.emailProperty().setValue(email);
    }

    public String getLast_name() {
        return first_nameProperty().get();
    }

    public void setGivenName(String givenName) {
        this.first_nameProperty().setValue(givenName);
    }

    public String getFamilyName() {
        return last_nameProperty().get();
    }

    public void setFamilyName(String familyName) {
        this.last_nameProperty().setValue(familyName);
    }

    public String getPhoneNumber() {
        return phone_numberProperty().get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phone_numberProperty().set(phoneNumber);
    }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty cityProperty() {
        return city;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty first_nameProperty() {
        return first_name;
    }

    public StringProperty last_nameProperty() {
        return last_name;
    }

    public StringProperty phone_numberProperty() {
        return phone_number;
    }

}
