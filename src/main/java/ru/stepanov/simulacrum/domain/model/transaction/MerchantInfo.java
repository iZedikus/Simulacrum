package ru.stepanov.simulacrum.domain.model.transaction;

public class MerchantInfo {
    private final String merchantName;
    private final int merchantCategoryCode;
    private final String merchantId;

    public MerchantInfo(String merchantName, int merchantCategoryCode, String merchantId) {
        this.merchantName = merchantName;
        this.merchantCategoryCode = merchantCategoryCode;
        this.merchantId = merchantId;
    }

    public String getMerchantName() { return merchantName; }
    public int getMerchantCategoryCode() { return merchantCategoryCode; }
    public String getMerchantId() { return merchantId; }
}
