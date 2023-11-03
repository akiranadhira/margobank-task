package com.margobank.transaction.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
        name = "Transaction Description Update",
        description = "Schema to hold Transaction Description Update information"
)
@Data
public class TransactionDescriptionUpdateDto {

    @Schema(
            description = "Transaction Reference Number",
            example = "202310221228559473"
    )
    @NotEmpty(message = "trxRefNo can not be a null or empty")
    @Pattern(regexp = "^$|[0-9]{18}", message = "trxRefNo must be 18 digits of numeric value")
    private String trxRefNo;

    @Schema(
            description = "Transaction Description",
            example = "FUND TRANSFER UPDATED"
    )
    @NotEmpty(message = "description can not be a null or empty")
    @Size(min = 5, max = 40, message = "The length of description should be between 3 and 40")
    private String description;

}
