package by.tr.web.service.validation.impl;

import by.tr.web.exception.service.common.LangNotSupportedException;
import by.tr.web.service.validation.DataTypeValidator;

public class DataTypeValidatorImpl implements DataTypeValidator {
    @Override
    public boolean checkLanguage(String lang) throws LangNotSupportedException{

        for (Language language : Language.values()) {
            String langName = language.name();
            if (langName.equalsIgnoreCase(lang)) {
                return true;
            }
        }
        throw new LangNotSupportedException("Language " + lang + " is not supported");
    }

    @Override
    public boolean checkForNotNegative(int start) {
        return start >= 0;
    }

    @Override
    public boolean checkForPositive(int size) {
        return size > 0;
    }
}
