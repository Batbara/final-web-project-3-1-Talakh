package by.tr.web.service.validation;

import by.tr.web.exception.service.common.EmptyParameterException;
import by.tr.web.exception.service.common.InvalidNumericalInput;
import by.tr.web.exception.service.common.LangNotSupportedException;
import by.tr.web.exception.service.common.ServiceException;

public interface DataTypeValidator {
    enum Language {
        RU, EN
    }
    boolean checkLanguage(String lang) throws LangNotSupportedException;
    boolean checkForNotNegative(String number) throws InvalidNumericalInput, EmptyParameterException;
    boolean checkForPositive(String number) throws InvalidNumericalInput, EmptyParameterException;

    default boolean validateInputParameters (int start, int size, String lang) throws ServiceException{
        if (!checkLanguage(lang)) {
            throw new LangNotSupportedException(lang + " language is not supported");
        }
        return true;
    }
}
