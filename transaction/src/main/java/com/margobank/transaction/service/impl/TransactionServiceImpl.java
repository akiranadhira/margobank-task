package com.margobank.transaction.service.impl;

import com.margobank.transaction.dto.TransactionDescriptionUpdateDto;
import com.margobank.transaction.dto.TransactionDto;
import com.margobank.transaction.entity.Account;
import com.margobank.transaction.entity.Customer;
import com.margobank.transaction.entity.Transaction;
import com.margobank.transaction.exception.ConcurrentUpdateException;
import com.margobank.transaction.exception.CustomerDoesNotExistException;
import com.margobank.transaction.exception.ResourceNotFoundException;
import com.margobank.transaction.mapper.TransactionMapper;
import com.margobank.transaction.repository.AccountRepository;
import com.margobank.transaction.repository.CustomerRepository;
import com.margobank.transaction.repository.TransactionRepository;
import com.margobank.transaction.service.ITransactionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private TransactionRepository transactionRepository;
    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    /**
     *
     * @param transactionDto - TransactionDto Object
     */
    @Override
    @Transactional
    public void createTransaction(TransactionDto transactionDto) {
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDto, new Transaction());
        Optional<Customer> optionalCustomer = customerRepository.findById(transaction.getCustomerId());
        if(optionalCustomer.isEmpty()) {
            throw new CustomerDoesNotExistException("Customer " + transaction.getCustomerId()+
                    " does not exist, cannot proceed with transaction." +
                    " Please contact our customer support");
        }
        Optional<Account> optionalAccount = accountRepository.findById(transaction.getAccountNumber());
        if(optionalAccount.isEmpty()) {
            throw new CustomerDoesNotExistException("Account " + transaction.getAccountNumber()+
                    " does not exist, cannot proceed with transaction." +
                    " Please contact our customer support");
        }
        transactionRepository.save(transaction);
    }

    /**
     *
     * @param page - Input Current Page value Start from 0
     * @param size - Input Page Size
     * @param customerId - Input Customer ID
     * @return List of Transactions based on given Customer ID
     */
    @Override
    public Page<TransactionDto> fetchTransactionsByCustomerId(int page, int size, String customerId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionPage = transactionRepository
                .findByCustomerIdOrderByCreatedDateDesc(pageable, customerId);
        return transactionPage.map(transaction ->
                TransactionMapper.mapToTransactionDto(transaction, new TransactionDto()));
    }

    /**
     * @param page        - Input Current Page value Start from 0
     * @param size        - Input Page Size
     * @param description - Input Description
     * @return List of Transactions based on given Description
     */
    @Override
    public Page<TransactionDto> fetchTransactionsByDescription(int page, int size, String description) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionPage = transactionRepository
                .findByDescriptionLikeOrderByCreatedDateDesc(pageable, "%" + description + "%");
        return transactionPage.map(transaction ->
                TransactionMapper.mapToTransactionDto(transaction, new TransactionDto()));
    }

    /**
     * @param page           - Input Current Page value Start from 0
     * @param size           - Input Page Size
     * @param accountNumbers - Input List of Account Number(s)
     * @return List of Transactions based on given Account Number(s)
     */
    @Override
    public Page<TransactionDto> fetchTransactionsByAccountNumbers(int page, int size, List<Long> accountNumbers) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionPage = transactionRepository
                .findByAccountNumberInOrderByCreatedDateDesc(pageable, accountNumbers);
        return transactionPage.map(transaction ->
                TransactionMapper.mapToTransactionDto(transaction, new TransactionDto()));
    }

    /**
     *
     * @param page - Input Current Page value Start from 0
     * @param size - Input Page Size
     * @return Transactions Page
     */
    @Override
    public Page<TransactionDto> fetchAllTransactions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactionPage = transactionRepository.findAllByOrderByCreatedDateDesc(pageable);
        return transactionPage.map(transaction ->
                TransactionMapper.mapToTransactionDto(transaction, new TransactionDto()));
    }

    /**
     *
     * @param transactionDescriptionUpdateDto - TransactionDescriptionUpdateDto Object
     * @return boolean indicating if the update of Transaction is successful or not
     */
    @Override
    @Transactional
    public boolean updateTransactionDescription(TransactionDescriptionUpdateDto transactionDescriptionUpdateDto) {
        boolean isUpdated = false;
        if(transactionDescriptionUpdateDto.getTrxRefNo() != null) {
            Transaction transaction = transactionRepository.findById(
                    Long.valueOf(transactionDescriptionUpdateDto.getTrxRefNo())).orElseThrow(
                    () -> new ResourceNotFoundException("Transaction", "Transaction Reference Number",
                            String.valueOf(transactionDescriptionUpdateDto.getTrxRefNo())));
            transaction.setDescription(transactionDescriptionUpdateDto.getDescription());

            try {
                transactionRepository.save(transaction);
            } catch (OptimisticLockingFailureException optimisticLockingFailureException) {
                throw new ConcurrentUpdateException("Concurrent update conflict, please try again");
            }
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     *
     * @param trxRefNo - Input Transaction Reference Number
     * @return boolean indicating if the deletion of Transaction is successful or not
     */
    @Override
    @Transactional
    public boolean deleteTransaction(String trxRefNo) {
        long transactionRefNo;
        try{
            transactionRefNo = Long.parseLong(trxRefNo);
        } catch (NumberFormatException e) {
            return false;
        }
        Transaction transaction = transactionRepository.findById(
                transactionRefNo).orElseThrow(
                () -> new ResourceNotFoundException("Transaction", "Transaction Reference Number", trxRefNo));
        transactionRepository.delete(transaction);
        return true;
    }
}
