package com.juantartaglia.conciprisma.conciliator.utils;

import com.juantartaglia.conciprisma.conciliator.config.Constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.juantartaglia.conciprisma.conciliator.config.Constants.TOQUE_PAN_LENGTH;
import static org.apache.logging.log4j.util.Strings.EMPTY;

public abstract class HashUtil {

    public enum Filter {
        TRANSACTION_DATE("^([^.]*)\\.", 0),
        PAYER_CARD_NUMBER("\\.([^.]*)\\.", 1),
        AMOUNT("^([^.]*)\\.", 2),
        AUTH_CODE("\\.([^.]*)\\.[^.]*$", 3),
        PAYMENT_AGGREGATOR("\\.[^.]*$", 4);

        private final String regex;
        private final int pos;

        public String getRegex(){
            return regex;
        }

        public int getPos(){
            return pos;
        }

        Filter(String regex, int pos) {
            this.regex = regex;
            this.pos = pos;
        }
    }

    public static String calcOperationHash(LocalDateTime transactionDate, String payerCardNumber, Double amount, Long authCode, Long paymentAggregator){
        return calcOperationHash(transactionDate, payerCardNumber, amount, authCode, paymentAggregator, Constants.DEFAULT_HASH_SEPARATOR);
    }

    public static String calcOperationHash(LocalDateTime transactionDate, String payerCardNumber, Double amount, Long authCode, Long paymentAggregator, String hashSeparator){
        String dateString = transactionDate != null ? transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "";
        String amountParsed = amount != null ? String.valueOf(amount.intValue()) : "";
        return String.format("%s%s%s%s%s%s%s%s%s", dateString, hashSeparator, truncPayerCardNumber(payerCardNumber), hashSeparator, amountParsed, hashSeparator, authCode == null ? "" : authCode, hashSeparator, paymentAggregator == null ? "" : paymentAggregator);
    }

    public static String calcOperationHash(LocalDate transactionDate, String payerCardNumber, Double amount, Long authCode, Long paymentAggregator, String hashSeparator){
        String dateString = transactionDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String amountParsed = amount != null ? String.valueOf(amount.intValue()) : "";
        return String.format("%s%s%s%s%s%s%s%s%s", dateString, hashSeparator, truncPayerCardNumber(payerCardNumber), hashSeparator, amountParsed, hashSeparator, authCode == null ? "" : authCode, hashSeparator, paymentAggregator == null ? "" : paymentAggregator);
    }

    public static String calcOperationHash(String payerCardNumber, Double amount){
        return calcOperationHash(truncPayerCardNumber(payerCardNumber), amount, Constants.DEFAULT_HASH_SEPARATOR);
    }

    public static String calcOperationHash(String payerCardNumber, Double amount, String hashSeparator){
        String amountParsed = amount != null ? String.valueOf(amount.intValue()) : "";
        return String.format("%s%s%s",truncPayerCardNumber(payerCardNumber), hashSeparator, amountParsed);
    }

    public static String calcOperationHashWithAuthCode(String payerCardNumber, Double amount, String authCode){
        return calcOperationHashWithAuthCode(payerCardNumber, amount, authCode, Constants.DEFAULT_HASH_SEPARATOR);
    }

    public static String calcOperationHashWithAuthCode(String payerCardNumber, Double amount, String authCode, String hashSeparator){
        String amountParsed = amount != null ? String.valueOf(amount.intValue()) : "";
        return String.format("%s%s%s%s%s",truncPayerCardNumber(payerCardNumber), hashSeparator, amountParsed, hashSeparator, authCode);
    }

    /**
      *  transformHash devuelve un hash omitiendo el o los valores de HashUtil.Filter,
      *  preservando separadores
     */
    public static String transformHash(String hashId, Filter... regexFilter) {
        List<String> hashList = List.of(hashId.split("\\.",-1));
        return IntStream.range(0,hashList.size())
                .mapToObj(i -> !Arrays.stream(regexFilter).map(Filter::getPos).collect(Collectors.toList()).contains(i) ? hashList.get(i) : "")
                .collect(Collectors.joining("."));
    }

    public static boolean isValidFullHashId(String hashId) {
        List<String> hashList = List.of(hashId.split("\\.",-1));
        return hashList.stream().noneMatch(String::isEmpty);
    }

    public static Object getValue(String hashId, Filter regexFilter) {
        List<String> hashList = List.of(hashId.split("\\.",-1));
        return hashList.get(regexFilter.getPos());
    }

    static String truncPayerCardNumber(String text) {
        Optional<String> result = Pattern.compile(".{1," + TOQUE_PAN_LENGTH + "}")
                .matcher(text)
                .results()
                .map(MatchResult::group)
                .findFirst();

        return result.orElse(EMPTY);

    }
}

