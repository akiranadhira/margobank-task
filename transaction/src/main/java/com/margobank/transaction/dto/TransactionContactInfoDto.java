package com.margobank.transaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Schema(
        name = "Transaction Contact Information",
        description = "Schema to hold Transaction Contact Information"
)
@ConfigurationProperties(prefix = "transaction")
public record TransactionContactInfoDto(String message, Map<String,String> contactDetails, List<String> onCallSupport) {
}
