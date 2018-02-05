package by.tr.web.service.validation.impl;

import by.tr.web.exception.service.common.EmptyParameterException;
import by.tr.web.exception.service.common.InvalidNumericalInput;
import by.tr.web.exception.service.common.LangNotSupportedException;
import by.tr.web.service.validation.DataTypeValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataTypeValidatorImpl implements DataTypeValidator {
    private final static String INTEGER_PATTERN = "\\d+";
    private final static Pattern CHECK_INT_PATTERN = Pattern.compile(INTEGER_PATTERN);

    @Override
    public boolean checkLanguage(String lang) throws LangNotSupportedException {

        for (Language language : Language.values()) {
            String langName = language.name();
            if (langName.equalsIgnoreCase(lang)) {
                return true;
            }
        }
        throw new LangNotSupportedException("Language " + lang + " is not supported");
    }

    @Override
    public boolean checkForNotNegative(String number) throws EmptyParameterException, InvalidNumericalInput {
        if (number == null) {
            throw new EmptyParameterException("Cannot check number for negative: parameter is empty");
        }
        Matcher matcher = CHECK_INT_PATTERN.matcher(number);

        if (matcher.matches()) {
            if (Integer.parseInt(number) <= 0) {
                throw new InvalidNumericalInput(number + " is negative");
            }
        } else {
            throw new InvalidNumericalInput(number + " is not a number");
        }
        return true;
    }

    @Override
    public boolean checkForPositive(String number) throws EmptyParameterException, InvalidNumericalInput {
        boolean isNotNegative = checkForNotNegative(number);
        if (isNotNegative) {
            if (Integer.parseInt(number) == 0) {
                throw new InvalidNumericalInput(number + " is zero");
            }
        }
        return true;
    }
}
