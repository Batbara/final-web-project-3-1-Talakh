package by.tr.web.service.validation;

import by.tr.web.exception.service.common.LangNotSupportedException;
import by.tr.web.exception.service.common.ServiceException;

public interface DataTypeValidator {
    enum Language {
        RU, EN
    }
    boolean checkLanguage(String lang) throws LangNotSupportedException;
    boolean checkForNotNegative(String number) throws ServiceException;
    boolean checkForPositive(String number) throws ServiceException;

    default boolean validateInputParameters (int start, int size, String lang) throws ServiceException{
        if (!checkLanguage(lang)) {
            throw new LangNotSupportedException(lang + " language is not supported");
        }
       /* if (!checkForNotNegative(start) || !checkForPositive(size)) {
            throw new InvalidNumericalInput("Invalid numerical number");
        }*/
        return true;
    }
}
