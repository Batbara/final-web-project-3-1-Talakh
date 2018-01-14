package by.tr.web.controller.constant;

public final class Util {
    private Util(){}
    public static int calcTableRecordsToTake(int recordsOnPage, int currentPage, int numberOfRecords){
        int numOfPages = (int) Math.ceil((double) numberOfRecords / recordsOnPage);
        int recordsToTake = recordsOnPage;
        if (recordsOnPage * currentPage > numberOfRecords) {
            if (numOfPages == currentPage) {
                recordsToTake = numberOfRecords;
            } else {
                recordsToTake = recordsOnPage * currentPage - numberOfRecords;
            }
        }
        return recordsToTake;
    }
}
