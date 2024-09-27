package ar.com.eldars.kencinas.challenge.utils;

import java.time.format.DateTimeFormatter;

public class CommonConstants {

    public static final String DATE_FORMAT = "dd-MM-yyyy";

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static final Integer CARD_NUMBER_VALID_DIGITS_COUNT = 16;

    public static final Integer CUSTOMER_DOCUMENT_MIN_VALID_DIGITS_COUNT = 7;

    public static final Integer CUSTOMER_DOCUMENT_MAX_VALID_DIGITS_COUNT = 8;

    public static final Double CARD_BRAND_RATE_MIN = 0.3;

    public static final Double CARD_BRAND_RATE_MAX = 5.0;

}
