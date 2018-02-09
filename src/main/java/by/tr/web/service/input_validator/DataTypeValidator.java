package by.tr.web.service.input_validator;

import by.tr.web.service.ServiceException;

public interface DataTypeValidator {
    enum Language {
        RU, EN
    }
    boolean checkLanguage(String lang) throws LangNotSupportedException;
    boolean checkForNotNegative(String number) throws InvalidNumericalInput, RequestParameterNotFound;
    boolean checkForPositive(String number) throws InvalidNumericalInput, RequestParameterNotFound;

    default boolean validateInputParameters (int start, int size, String lang) throws ServiceException{
        if (!checkLanguage(lang)) {
            throw new LangNotSupportedException(lang + " language is not supported");
        }
        return true;
    }
}
