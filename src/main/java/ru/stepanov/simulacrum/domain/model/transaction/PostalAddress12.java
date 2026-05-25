package ru.stepanov.simulacrum.domain.model.transaction;

import lombok.Value;

import java.util.List;

@Value
public class PostalAddress12 {
    String streetName;
    String buildingNumber;
    String postCode;
    String townName;
    String country;
    List<String> addressLine;
}
