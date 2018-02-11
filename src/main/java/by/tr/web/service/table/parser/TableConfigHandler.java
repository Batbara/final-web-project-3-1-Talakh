package by.tr.web.service.table.parser;

import by.tr.web.domain.Table;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;

public class TableConfigHandler extends DefaultHandler {
    private Map<String, Table> configurations;
    private Table configuration;
    private StringBuilder text;

    public TableConfigHandler() {
        super();
        configurations = new HashMap<>();
    }

    public Map<String, Table> getConfigurations() {
        return configurations;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        text = new StringBuilder();
        if (localName.equals(TableXmlParameter.TABLE)) {
            configuration = new Table();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (localName) {
            case TableXmlParameter.TABLE_NAME:
                configuration.setTableName(text.toString());
                break;
            case TableXmlParameter.CURRENT_PAGE:
                int currentPage = Integer.parseInt(text.toString());
                configuration.setCurrentPage(currentPage);
                break;
            case TableXmlParameter.RECORDS_ON_PAGE:
                int recordsOnPage = Integer.parseInt(text.toString());
                configuration.setRecordsOnPage(recordsOnPage);
                break;
            case TableXmlParameter.ORDER_TYPE:
                configuration.setOrderType(text.toString());
                break;
            case TableXmlParameter.TABLE:
                configurations.put(configuration.getTableName(), configuration);
                configuration = null;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        text.append(ch, start, length);
    }
}
