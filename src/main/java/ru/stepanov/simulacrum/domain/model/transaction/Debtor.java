package ru.stepanov.simulacrum.domain.model.transaction;

import lombok.Value;

import java.util.List;

@Value
public class Debtor {
    String name;
    List<IdentificationType> identification;
    MerchantInfo merchantInformation;
    PostalAddress12 postalAddress;
}
