package by.tr.web.service.validation;

import by.tr.web.exception.service.common.InvalidNumericalInput;
import by.tr.web.exception.service.common.LangNotSupportedException;
import by.tr.web.exception.service.common.ServiceException;

public interface DataTypeValidator {
    enum Language {
        RU, EN
    }
    boolean checkLanguage(String lang);
    boolean checkStartValue(int startRecordNum);
    boolean checkSize (int size);

    default boolean validateInputParameters (int start, int size, String lang) throws ServiceException{
        if (!checkLanguage(lang)) {
            throw new LangNotSupportedException(lang + " language is not supported");
        }
        if (!checkStartValue(start) || !checkSize(size)) {
            throw new InvalidNumericalInput("Invalid numerical number");
        }
        return true;
    }
}
