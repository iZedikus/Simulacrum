package ru.stepanov.simulacrum.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountPageResponse {
    private List<AccountResponse> content;
    private PageMetaResponse meta;
}
