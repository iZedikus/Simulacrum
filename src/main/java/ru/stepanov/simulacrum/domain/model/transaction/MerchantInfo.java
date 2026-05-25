package ru.stepanov.simulacrum.domain.model.transaction;

import lombok.Value;

@Value
public class MerchantInfo {
    String merchantName;
    int merchantCategoryCode;
    String merchantId;
}
