package by.tr.web.service.show;

import by.tr.web.service.InvalidOrderTypeException;

public interface ShowValidator {

    boolean checkOrderType(String orderType) throws InvalidOrderTypeException;

    boolean isOrderDescending(String orderType) throws InvalidOrderTypeException;

}
