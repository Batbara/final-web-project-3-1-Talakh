package by.tr.web.service.input_validator;

public interface DataTypeValidator {
    /**
     * Check if language is provided by application
     *
     * @param lang String to check
     * @return true if language is supported
     * @throws LangNotSupportedException If language is not provided by application
     */
    boolean checkLanguage(String lang) throws LangNotSupportedException;

    /**
     * Check if input String is more or equals zero
     *
     * @param number String to check
     * @return true if String is not negative number
     * @throws InvalidNumericalInput    If input is invalid number/not a number
     * @throws RequestParameterNotFound If input is null
     */
    boolean checkForNotNegative(String number) throws InvalidNumericalInput, RequestParameterNotFound;
    /**
     * Check if input String is more than zero
     *
     * @param number String to check
     * @return true if input is positive number
     * @throws InvalidNumericalInput    If input is invalid number/not a number
     * @throws RequestParameterNotFound If input is null
     */
    boolean checkForPositive(String number) throws InvalidNumericalInput, RequestParameterNotFound;

}
