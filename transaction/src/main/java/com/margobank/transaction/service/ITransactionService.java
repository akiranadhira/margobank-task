package com.margobank.transaction.service;

import com.margobank.transaction.dto.TransactionDescriptionUpdateDto;
import com.margobank.transaction.dto.TransactionDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITransactionService {

    /**
     *
     * @param transactionDto - TransactionDto Object
     */
    void createTransaction(TransactionDto transactionDto);

    /**
     *
     * @param page - Input Current Page value Start from 0
     * @param size - Input Page Size
     * @param customerId - Input Customer ID
     * @return List of Transactions based on given Customer ID
     */
    Page<TransactionDto> fetchTransactionsByCustomerId(int page, int size, String customerId);

    /**
     *
     * @param page - Input Current Page value Start from 0
     * @param size - Input Page Size
     * @param description - Input Description
     * @return List of Transactions based on given Description
     */
    Page<TransactionDto> fetchTransactionsByDescription(int page, int size, String description);


    /**
     *
     * @param page - Input Current Page value Start from 0
     * @param size - Input Page Size
     * @param accountNumbers - Input List of Account Number(s)
     * @return List of Transactions based on given Account Number(s)
     */
    Page<TransactionDto> fetchTransactionsByAccountNumbers(int page, int size, List<Long> accountNumbers);

    /**
     *
     * @param page - Input Current Page value Start from 0
     * @param size - Input Page Size
     * @return Transactions Page
     */
    Page<TransactionDto> fetchAllTransactions(int page, int size);

    /**
     *
     * @param transactionDescriptionUpdateDto - TransactionDescriptionUpdateDto Object
     * @return boolean indicating if the update of Transaction is successful or not
     */
    boolean updateTransactionDescription(TransactionDescriptionUpdateDto transactionDescriptionUpdateDto);

    /**
     *
     * @param trxRefNo - Input Transaction Reference Number
     * @return boolean indicating if the deletion of Transaction is successful or not
     */
    boolean deleteTransaction(String trxRefNo);

}
