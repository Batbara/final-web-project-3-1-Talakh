package by.tr.web.service.validation.impl;

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
    public boolean checkForNotNegative(String number) {
        if(number == null){
            return false;
        }
        Matcher matcher = CHECK_INT_PATTERN.matcher(number);
        if (matcher.matches()) {
            return Integer.parseInt(number) >= 0;
        }
        return false;
    }

    @Override
    public boolean checkForPositive(String number) {
        boolean isNotNegative = checkForNotNegative(number);
        if(isNotNegative){
            return Integer.parseInt(number) != 0;
        }
        return false;
    }
}
