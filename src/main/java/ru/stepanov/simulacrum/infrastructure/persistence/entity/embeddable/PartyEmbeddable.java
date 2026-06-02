package ru.stepanov.simulacrum.infrastructure.persistence.entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartyEmbeddable {
    private String name;
    private String streetName;
    private String buildingNumber;
    private String postCode;
    private String townName;
    private String country;
}
