package by.tr.web.service.validation.impl;

import by.tr.web.service.validation.DataTypeValidator;

public class DataTypeValidatorImpl implements DataTypeValidator {
    @Override
    public boolean checkLanguage(String lang) {

        for (Language language : Language.values()) {
            String langName = language.name();
            if (langName.equalsIgnoreCase(lang)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean checkStartValue(int start) {
        return start >= 0;
    }

    @Override
    public boolean checkSize(int size) {
        return size > 0;
    }
}
