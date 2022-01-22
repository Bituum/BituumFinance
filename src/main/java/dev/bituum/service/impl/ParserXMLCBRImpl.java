package dev.bituum.service.impl;

import dev.bituum.model.Quotes;
import dev.bituum.service.ParserXMLCBR;
import dev.bituum.util.CBRHandler;
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
public class ParserXMLCBRImpl implements ParserXMLCBR {
    private static final String PATH = "/home/bituum/IdeaProjects/BituumFinance/src/main/resources/static/CBRQuotes.xml";
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
        return null;
    }
}
