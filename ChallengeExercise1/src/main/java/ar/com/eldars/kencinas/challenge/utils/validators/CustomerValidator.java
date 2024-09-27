package ar.com.eldars.kencinas.challenge.utils.validators;

import ar.com.eldars.kencinas.challenge.utils.CommonConstants;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CustomerValidator {
    /**
     * Validates if the given name is ok:
     * - Checks if the given string is not blank (or empty)
     * - Checks that only alpha and space characters are allowed in the string
     *
     * @param name any name (firstname, surname)
     * @return is name ok?
     */
    public static Boolean anyName(String name) {
        return StringUtils.isNotBlank(name) && StringUtils.isAlphaSpace(name);
    }

    /**
     * Validates the given document number:
     * - Checks if the given string is a number
     * - Checks if the given string does not contain leading zeros
     * - Checks if the given string number has a character count between CUSTOMER_DOCUMENT_MIN_VALID_DIGITS_COUNT and CUSTOMER_DOCUMENT_MAX_VALID_DIGITS_COUNT
     *
     * @param document customer document number
     * @return is document ok?
     */
    public static Boolean document(String document) {
        return StringUtils.isNumeric(document)
                && StringUtils.stripStart(document, "0").equals(document)
                && document.length() >= CommonConstants.CUSTOMER_DOCUMENT_MIN_VALID_DIGITS_COUNT
                && document.length() <= CommonConstants.CUSTOMER_DOCUMENT_MAX_VALID_DIGITS_COUNT;
    }

    /**
     * Validates if the given date of birth:
     * - Checks if the given string is a valid date with DATE_FORMATTER
     * - Checks if the dob is before now
     *
     * @param dob date of birth string
     * @return is dob ok?
     */
    public static Boolean dob(String dob) {
        try {
            LocalDate birth = LocalDate.parse(dob, CommonConstants.DATE_FORMATTER);
            return birth.isBefore(LocalDate.now());
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    /**
     * Checks if the given stirng has at least one "@"
     *
     * @param email email
     * @return is email ok?
     */
    public static Boolean email(String email) {
        return StringUtils.isNotBlank(email) && StringUtils.countMatches(email, "@") == 1;
    }
}
