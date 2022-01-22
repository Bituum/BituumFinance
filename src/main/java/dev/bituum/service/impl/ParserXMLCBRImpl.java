package dev.bituum.service.impl;

import dev.bituum.model.Quotes;
import dev.bituum.service.ParserXMLCBR;
import dev.bituum.util.CBRHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@PropertySource("classpath:/config/resourcePath.properties")
public class ParserXMLCBRImpl implements ParserXMLCBR {
    @Value("${path.xml}")
    private String PATH;
    private final SAXParserFactory factory = SAXParserFactory.newInstance();
    private static Logger logger = Logger.getLogger(ParserXMLCBRImpl.class.getName());
    private List<Quotes> resultList = new ArrayList<>();

    @Override
    public List<Quotes> parse() throws ParserConfigurationException, SAXException, IOException {
        SAXParser parser = factory.newSAXParser();

        CBRHandler handler = new CBRHandler(resultList);
        parser.parse(new File(PATH), handler);
        resultList = handler.getQuotesList();
        System.out.println(resultList.toString());
        return resultList;
    }
}
