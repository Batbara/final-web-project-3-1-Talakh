package by.tr.web.domain;

public class Table {
    private String tableName;
    private int currentPage;
    private int recordsOnPage;
    private String orderType;

    public Table() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRecordsOnPage() {
        return recordsOnPage;
    }

    public void setRecordsOnPage(int recordsOnPage) {
        this.recordsOnPage = recordsOnPage;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
