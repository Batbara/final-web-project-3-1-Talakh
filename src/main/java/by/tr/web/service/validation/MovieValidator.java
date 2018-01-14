package by.tr.web.service.validation;

public interface MovieValidator{
    enum OrderType {
        TITLE, YEAR, RATING
    }

    boolean checkOrderType(String orderType);

}
