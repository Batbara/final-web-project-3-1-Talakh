package by.tr.web.service.validation.impl;

import by.tr.web.service.validation.MovieValidator;

public class MovieValidatorImpl implements MovieValidator {
    @Override
    public boolean checkOrderType(String inputOrderType) {
        for (OrderType orderType : OrderType.values()) {
            String orderTypeName = orderType.name();
            if (orderTypeName.equalsIgnoreCase(inputOrderType)) {
                return true;
            }
        }
        return false;
    }
}
