package com.margobank.transaction.controller;

import com.margobank.transaction.constants.TransactionConstants;
import com.margobank.transaction.dto.*;
import com.margobank.transaction.service.ITransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Tag(
        name = "CRUD REST APIs for Transaction in margobank Task",
        description = "CRUD REST APIs in margobank Task to CREATE, UPDATE, FETCH, and DELETE Transactions"
)
@RestController
@RequestMapping(path = "/api/v1/transaction", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final Environment environment;
    private final TransactionContactInfoDto transactionContactInfoDto;
    private final ITransactionService iTransactionService;

    public TransactionController(Environment environment,
                                 TransactionContactInfoDto transactionContactInfoDto,
                                 ITransactionService iTransactionService) {
        this.environment = environment;
        this.transactionContactInfoDto = transactionContactInfoDto;
        this.iTransactionService = iTransactionService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Operation(
            summary = "Create Transaction REST API",
            description = "REST API to create new transaction inside margobank Task"
    )
    @ApiResponses({
            @ApiResponse (
                    responseCode = "00",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse (
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema (
                                    implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createTransaction(@RequestHeader("margobank-trace-id") String traceId,
                                                         @Valid @RequestBody TransactionDto transactionDto) {
        logger.debug("margobank-trace-id at createTransaction found {} ", traceId);
        iTransactionService.createTransaction(transactionDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(TransactionConstants.STATUS_00, TransactionConstants.MESSAGE_00));
    }

    @Operation(
            summary = "Fetch Transaction REST API by Customer ID",
            description = "REST API to fetch transactions inside margobank Task based on Customer ID"
    )
    @ApiResponses({
            @ApiResponse (
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse (
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema (
                                    implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetchTransactionsByCustomerId")
    public ResponseEntity<Page<TransactionDto>> fetchTransactionsByCustomerId(
                              @RequestHeader("margobank-trace-id") String traceId,
                              @Valid @RequestParam @NotEmpty(message = "Page can not be a null or empty")
                              @Pattern(regexp = "^$|[0-9]", message = "Page must be numeric value")
                              @Size(min = 1, max = 3, message = "The length of Page should be between 1 and 3")
                              String page,
                              @Valid @RequestParam @NotEmpty(message = "Size can not be a null or empty")
                              @Pattern(regexp = "^$|[0-9]", message = "Size must be numeric value")
                              @Size(min = 1, max = 3, message = "The length of Size should be between 1 and 3")
                              String size,
                              @Valid @RequestParam @NotEmpty(message = "customerId can not be a null or empty")
                              @Size(min = 3, max = 40, message = "The length of customerId should be between 5 and 40")
                              String customerId) {
        logger.debug("margobank-trace-id at fetchTransactionsByCustomerId found {} ", traceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iTransactionService.fetchTransactionsByCustomerId(
                        Integer.parseInt(page), Integer.parseInt(size),customerId));
    }

    @Operation(
            summary = "Fetch Transaction REST API by Account Numbers(s)",
            description = "REST API to fetch transactions inside margobank Task based on Account Numbers(s)"
    )
    @ApiResponses({
            @ApiResponse (
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse (
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema (
                                    implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetchTransactionsByAccountNumbers")
    public ResponseEntity<Page<TransactionDto>> fetchTransactionsByAccountNumbers(
            @RequestHeader("margobank-trace-id") String traceId,
            @Valid @RequestParam @NotEmpty(message = "Page can not be a null or empty")
            @Pattern(regexp = "^$|[0-9]", message = "Page must be numeric value")
            @Size(min = 1, max = 3, message = "The length of Page should be between 1 and 3")
            String page,
            @Valid @RequestParam @NotEmpty(message = "Size can not be a null or empty")
            @Pattern(regexp = "^$|[0-9]", message = "Size must be numeric value")
            @Size(min = 1, max = 3, message = "The length of Size should be between 1 and 3")
            String size,
            @Valid @RequestParam @NotEmpty(message = "accountNumbers can not be a null or empty")
            @Pattern(regexp = "\\b\\d{10}(?!,)\\b|(^\\d{10},)*\\b\\d{10}\\b",
                    message = "accountNumbers must be 10 digits of numeric value, multiple account allowed with , as a separator")
            String accountNumbers) {
        logger.debug("margobank-trace-id at fetchTransactionsByAccountNumbers found {} ", traceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iTransactionService.fetchTransactionsByAccountNumbers(
                        Integer.parseInt(page), Integer.parseInt(size),
                        Arrays.stream(accountNumbers.split(",")).map(Long::parseLong).toList()));
    }

    @Operation(
            summary = "Fetch Transaction REST API by Description",
            description = "REST API to fetch transactions inside margobank Task based on Description"
    )
    @ApiResponses({
            @ApiResponse (
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse (
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema (
                                    implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetchTransactionsByDescription")
    public ResponseEntity<Page<TransactionDto>> fetchTransactionsByDescription(
            @RequestHeader("margobank-trace-id") String traceId,
            @Valid @RequestParam @NotEmpty(message = "Page can not be a null or empty")
            @Pattern(regexp = "^$|[0-9]", message = "Page must be numeric value")
            @Size(min = 1, max = 3, message = "The length of Page should be between 1 and 3")
            String page,
            @Valid @RequestParam @NotEmpty(message = "Size can not be a null or empty")
            @Pattern(regexp = "^$|[0-9]", message = "Size must be numeric value")
            @Size(min = 1, max = 3, message = "The length of Size should be between 1 and 3")
            String size,
            @Valid @RequestParam @NotEmpty(message = "customerId can not be a null or empty")
            @Size(min = 5, max = 100, message = "The length of description should be between 5 and 100")
            String description) {
        logger.debug("margobank-trace-id at fetchTransactionsByDescription found {} ", traceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iTransactionService.fetchTransactionsByDescription(
                        Integer.parseInt(page), Integer.parseInt(size),description));
    }

    @Operation(
            summary = "Fetch All Transaction REST API",
            description = "REST API to fetch All transactions inside margobank Task"
    )
    @ApiResponses({
            @ApiResponse (
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse (
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema (
                                    implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetchAllTransactions")
    public ResponseEntity<Page<TransactionDto>> fetchAllTransactions(
            @RequestHeader("margobank-trace-id") String traceId,
            @Valid @RequestParam @NotEmpty(message = "Page can not be a null or empty")
            @Pattern(regexp = "^$|[0-9]", message = "Page must be numeric value")
            @Size(min = 1, max = 3, message = "The length of Page should be between 1 and 3")
            String page,
            @Valid @RequestParam @NotEmpty(message = "Size can not be a null or empty")
            @Pattern(regexp = "^$|[0-9]", message = "Size must be numeric value")
            @Size(min = 1, max = 3, message = "The length of Size should be between 1 and 3")
            String size) {
        logger.debug("margobank-trace-id at fetchAllTransactions found {} ", traceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iTransactionService.fetchAllTransactions(Integer.parseInt(page), Integer.parseInt(size)));
    }

    @Operation(
            summary = "Update Transaction REST API",
            description = "REST API to update transaction description inside margobank Task based on Transaction Reference Number"
    )
    @ApiResponses({
            @ApiResponse (
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse (
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse (
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema (
                                    implementation = ErrorResponseDto.class)
                            )
            )
    })
    @PutMapping("/updateTransactionDescription")
    public ResponseEntity<ResponseDto> updateTransactionDescription(@RequestHeader("margobank-trace-id") String traceId,
            @Valid @RequestBody TransactionDescriptionUpdateDto transactionDescriptionUpdateDto) {
        logger.debug("margobank-trace-id at updateTransactionDescription found {} ", traceId);
        boolean isUpdated = iTransactionService.updateTransactionDescription(transactionDescriptionUpdateDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(TransactionConstants.STATUS_200, TransactionConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(TransactionConstants.STATUS_409, TransactionConstants.MESSAGE_409_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Transaction REST API",
            description = "REST API to delete transaction inside margobank Task based on Transaction Reference Number"
    )
    @ApiResponses({
            @ApiResponse (
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse (
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse (
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema (
                                    implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/deleteTransaction")
    public ResponseEntity<ResponseDto> deleteTransaction(@RequestHeader("margobank-trace-id") String traceId,
                                         @Valid @RequestParam @NotEmpty(message = "trxRefNo can not be a null or empty")
                                         @Pattern(regexp = "^$|[0-9]{18}",
                                                 message = "trxRefNo must be 18 digits of numeric value")
                                         String trxRefNo) {
        logger.debug("margobank-trace-id at deleteTransaction found {} ", traceId);
        boolean isDeleted = iTransactionService.deleteTransaction(trxRefNo);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(TransactionConstants.STATUS_200, TransactionConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(TransactionConstants.STATUS_409, TransactionConstants.MESSAGE_500_DELETE));
        }
    }

    @Operation(
            summary = "Get Build Information",
            description = "Get Build information that is deployed into transaction microservice"
    )
    @ApiResponses({
            @ApiResponse (
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse (
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema (
                                    implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(@RequestHeader("margobank-trace-id") String traceId) {
        logger.debug("margobank-trace-id at getBuildInfo found {} ", traceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @Operation(
            summary = "Get Java Version",
            description = "Get Java version details that is installed into transaction microservice"
    )
    @ApiResponses({
            @ApiResponse (
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse (
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema (
                                    implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(@RequestHeader("margobank-trace-id") String traceId) {
        logger.debug("margobank-trace-id at getJavaVersion found {} ", traceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get Contact Info",
            description = "Contact Info details that can be reached out in case of any issues"
    )
    @ApiResponses({
            @ApiResponse (
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse (
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema (
                                    implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<TransactionContactInfoDto> getContactInfo(@RequestHeader("margobank-trace-id") String traceId) {
        logger.debug("margobank-trace-id at getContactInfo found {} ", traceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(transactionContactInfoDto);
    }
}
