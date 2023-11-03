package com.margobank.transaction.dto;

import com.margobank.transaction.validator.ValidLocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Transaction",
        description = "Schema to hold Transaction information"
)
public class TransactionDto {

    @Schema(
            description = "Transaction Reference Number (Remove it from JSON Request for Data Entry)",
            example = "202310221228559473"
    )
    @Pattern(regexp = "^$|[0-9]{18}", message = "trxRefNo must be 18 digits of numeric value")
    private String trxRefNo;

    @Schema(
            description = "Account Number", example = "8872838283"
    )
    @NotEmpty(message = "accountNumber can not be a null or empty")
    @Pattern(regexp = "^$|[0-9]{10}", message = "accountNumber must be 10 digits of numeric value")
    private String accountNumber;

    @Schema(
            description = "Transaction Amount", example = "581.00"
    )
    @NotEmpty(message = "trxAmount can not be a null or empty")
    @Pattern(regexp = "^\\d{1,18}(\\.\\d{1,2})?$",
            message = "trxAmount should be numeric value with maximum 18 digit value with 2 digits decimal value")
    private String trxAmount;

    @Schema(
            description = "Description", example = "FUND TRANSFER"
    )
    @NotEmpty(message = "description can not be a null or empty")
    @Size(min = 5, max = 100, message = "The length of description should be between 5 and 100")
    private String description;

    @Schema(
            description = "Transaction Date", example = "2023-10-22T12:34:56"
    )
    @NotEmpty(message = "trxDate can not be a null or empty")
    @ValidLocalDateTime(pattern = "yyyy-MM-dd'T'HH:mm:ss",
            message = "The format of trxDate should be yyyy-MM-dd'T'HH:mm:ss (2023-10-22T12:34:56)")
    private String trxDate;

    @Schema(
            description = "Transaction Time", example = "2023-10-22T12:34:56"
    )
    @NotEmpty(message = "trxTime can not be a null or empty")
    @ValidLocalDateTime(pattern = "yyyy-MM-dd'T'HH:mm:ss",
            message = "The format of trxTime should be yyyy-MM-dd'T'HH:mm:ss (2023-10-22T12:34:56)")
    private String trxTime;

    @Schema(
            description = "Customer ID", example = "222"
    )
    @NotEmpty(message = "customerId can not be a null or empty")
    @Size(min = 3, max = 40, message = "The length of customerId should be between 3 and 40")
    private String customerId;



}
