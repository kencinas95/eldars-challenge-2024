package ar.com.eldars.kencinas.challenge.utils.validators;

import ar.com.eldars.kencinas.challenge.models.CardBrand;
import ar.com.eldars.kencinas.challenge.utils.CommonConstants;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CardValidator {
    /**
     * Validates the given number string:
     * - Checks if the given string is a number
     * - Checks if the given string does not contain leading zeros
     * - Checks if the given string has a length of the defined CARD_NUMBER_VALID_DIGITS_COUNT
     *
     * @param number card number string
     * @return is valid card number?
     */
    public static Boolean number(String number) {
        return StringUtils.isNumeric(number)
                && StringUtils.stripStart(number, "0").equals(number)
                && number.length() == CommonConstants.CARD_NUMBER_VALID_DIGITS_COUNT;
    }

    /**
     * Validates the given brand string:
     * - Checks if the given string is not blank
     * - Checks if the given string is a defined brand
     *
     * @param brand card brand name
     * @return is card brand valid?
     */
    public static Boolean brand(String brand) {
        return StringUtils.isNotBlank(brand) && CardBrand.checkValue(brand);
    }

    /**
     * Validates the given expiration date string:
     * - Parses it to a Date by using the DATE_FORMAT*
     *
     * @param expiration card expiration date
     * @return is card expiration date ok?
     */
    public static Boolean expiration(String expiration) {
        try {
            LocalDate.parse(expiration, CommonConstants.DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    public static Double rate(Double rate)
    {
        if (rate < CommonConstants.CARD_BRAND_RATE_MIN) return CommonConstants.CARD_BRAND_RATE_MIN;
        if (rate > CommonConstants.CARD_BRAND_RATE_MAX) return CommonConstants.CARD_BRAND_RATE_MAX;
        return rate;
    }
}
