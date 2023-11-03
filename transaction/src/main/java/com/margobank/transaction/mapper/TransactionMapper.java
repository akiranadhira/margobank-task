package com.margobank.transaction.mapper;

import com.margobank.transaction.dto.TransactionDto;
import com.margobank.transaction.entity.Transaction;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

public class TransactionMapper {

    public static TransactionDto mapToTransactionDto(Transaction transaction, TransactionDto transactionDto) {

        transactionDto.setTrxRefNo(String.valueOf(transaction.getTrxRefNo()));
        transactionDto.setAccountNumber(String.valueOf(transaction.getAccountNumber()));
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setTrxAmount(String.valueOf(transaction.getTrxAmount()));
        transactionDto.setTrxDate(transaction.getTrxDate().toString());
        transactionDto.setTrxTime(transaction.getTrxTime().toString());
        transactionDto.setCustomerId(transaction.getCustomerId());

        return transactionDto;
    }

    public static Transaction mapToTransaction(TransactionDto transactionDto, Transaction transaction) {

        if(transaction.getTrxRefNo() == null) {
            transaction.setTrxRefNo(generateTransactionReference());
        }

        transaction.setAccountNumber(Long.valueOf(transactionDto.getAccountNumber()));
        transaction.setDescription(transactionDto.getDescription());
        transaction.setTrxAmount(new BigDecimal(transactionDto.getTrxAmount()));
        transaction.setTrxDate(LocalDateTime.parse(transactionDto.getTrxDate()));
        transaction.setTrxTime(LocalDateTime.parse(transactionDto.getTrxTime()));
        transaction.setCustomerId(transactionDto.getCustomerId());

        return transaction;
    }

    public static Long generateTransactionReference() {
        // Get the current timestamp
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        // Generate a random 4-digit numeric component
        Random rand = new Random();
        int randomNumericPart = rand.nextInt(1_000_0);

        // Format the numeric part as a 4-digit string
        String numericPart = String.format("%04d", randomNumericPart);

        // Combine timestamp and numeric part to form an 18-digit reference number
        return Long.valueOf(dateFormat.format(now) + numericPart);
    }
}
