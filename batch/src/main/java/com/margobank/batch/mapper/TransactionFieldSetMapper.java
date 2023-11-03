package com.margobank.batch.mapper;

import com.margobank.batch.entity.Transaction;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.batch.item.file.transform.FieldSet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TransactionFieldSetMapper extends BeanWrapperFieldSetMapper<Transaction> {

    private final DateTimeFormatter dateFormatter;
    private final DateTimeFormatter timeFormatter;

    public TransactionFieldSetMapper() {
        this.setTargetType(Transaction.class);
        this.dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    }

    @Override
    protected void initBinder(DataBinder dataBinder) {
        // Customize the DataBinder if needed
    }

    @Override
    public Transaction mapFieldSet(FieldSet fieldSet) throws BindException {

        // Map the fields to a Transaction object
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(Long.parseLong(fieldSet.readString("accountNumber")));
        transaction.setTrxAmount(new BigDecimal(fieldSet.readString("trxAmount")));
        transaction.setDescription(fieldSet.readString("description"));

        // Convert String to LocalDateTime for trxDate and trxTime fields
        LocalDate trxDate = LocalDate.parse(fieldSet.readString("trxDate"), dateFormatter);
        LocalTime trxTime = LocalTime.parse(fieldSet.readString("trxTime"), timeFormatter);

        // Set the converted values
        transaction.setTrxDate(trxDate.atTime(LocalTime.MIDNIGHT));
        transaction.setTrxTime(trxTime.atDate(trxDate));

        transaction.setCustomerId(fieldSet.readString("customerId"));

        return transaction;
    }

}

