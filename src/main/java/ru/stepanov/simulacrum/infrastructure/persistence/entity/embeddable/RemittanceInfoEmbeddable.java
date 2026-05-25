package ru.stepanov.simulacrum.infrastructure.persistence.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemittanceInfoEmbeddable {
    private String remittanceUnstructured;
}
