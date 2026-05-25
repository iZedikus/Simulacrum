package ru.stepanov.simulacrum.domain.model.transaction;

import java.util.List;

public class PostalAddress12 {
    private final String streetName;
    private final String buildingNumber;
    private final String postCode;
    private final String townName;
    private final String country;
    private final List<String> addressLine;

    public PostalAddress12(String streetName, String buildingNumber, String postCode, String townName, String country, List<String> addressLine) {
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.postCode = postCode;
        this.townName = townName;
        this.country = country;
        this.addressLine = addressLine;
    }
    public String getStreetName() { return streetName; }
    public String getBuildingNumber() { return buildingNumber; }
    public String getPostCode() { return postCode; }
    public String getTownName() { return townName; }
    public String getCountry() { return country; }
    public List<String> getAddressLine() { return addressLine; }
}
