package ru.stepanov.simulacrum.domain.model.transaction;

import java.util.List;

public class Creditor {
    private final String name;
    private final List<IdentificationType> identification;
    private final MerchantInfo merchantInformation;
    private final PostalAddress12 postalAddress;

    public Creditor(String name, List<IdentificationType> identification, MerchantInfo merchantInformation, PostalAddress12 postalAddress) {
        this.name = name;
        this.identification = identification;
        this.merchantInformation = merchantInformation;
        this.postalAddress = postalAddress;
    }

    public String getName() { return name; }
    public List<IdentificationType> getIdentification() { return identification; }
    public MerchantInfo getMerchantInformation() { return merchantInformation; }
    public PostalAddress12 getPostalAddress() { return postalAddress; }
}
