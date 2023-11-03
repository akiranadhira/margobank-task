package com.margobank.batch.config;

import com.margobank.batch.entity.Transaction;
import org.springframework.batch.item.ItemProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {

    @Override
    public Transaction process(Transaction transaction) {
        transaction.setTrxRefNo(this.generateTransactionReference());
        return transaction;
    }

    private Long generateTransactionReference() {
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
