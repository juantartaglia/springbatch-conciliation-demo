package com.juantartaglia.conciprisma.conciliator.Transaction;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper  implements RowMapper<Transaction> {
    @Override
    public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
        Transaction trx = new Transaction();
        trx.setRefNumber(rs.getString("refNumber"));
        trx.setPayerCardNumber(rs.getString("payerCardNumber"));
        trx.setTransactionDate(rs.getTimestamp("transactionDate"));
        trx.setAmount(rs.getDouble("amount"));
        trx.setPayerCardBrand(rs.getString("payerCardBrand"));
        trx.setChannel(rs.getString("channel"));
        trx.setAuthCode(rs.getString("authCode"));
        trx.setPaymentAggregator(rs.getString("paymentAggregator"));
        trx.setExternalRef(rs.getString("externalRef"));
        trx.setClientId(rs.getString("clientId"));
        trx.setStatus(rs.getString("status"));
        return trx;
    }
}
