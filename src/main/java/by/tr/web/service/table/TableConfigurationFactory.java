package by.tr.web.service.table;

import by.tr.web.controller.util.TypeFormatUtil;
import by.tr.web.service.table.parser.TableConfigHandler;
import by.tr.web.service.table.parser.TableConfiguration;
import by.tr.web.service.table.parser.TableXmlParameter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TableConfigurationFactory {
    private static TableConfigurationFactory instance = new TableConfigurationFactory();
    private Map<String, TableConfiguration> configurations;

    private TableConfigurationFactory() {
    }

    public static TableConfigurationFactory getInstance() {
        return instance;
    }

    public TableConfiguration configurationFor(String name) throws TableConfigurationException {
        if (configurations == null) {
            throw new TableConfigurationException("TableConfigurationFactory wasn't initialised");
        }
        TableConfiguration configuration = configurations.get(name);
        if (configuration == null) {
            throw new TableConfigurationException("No configuration for name " + name);
        }
        return configuration;
    }

    public void initFactory() throws TableConfigurationException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        parserFactory.setNamespaceAware(true);
        try {
            SAXParser parser = parserFactory.newSAXParser();
            TableConfigHandler handler = new TableConfigHandler();

            String sourcePath = TypeFormatUtil.getSourcePath(TableXmlParameter.TABLE_CONFIGURATION_XML_PATH);
            parser.parse(new File(sourcePath), handler);

            configurations = handler.getConfigurations();
        } catch (IOException | ParserConfigurationException | SAXException e) {
            throw new TableConfigurationException("Error while parsing table configuration", e);
        }
    }
}
