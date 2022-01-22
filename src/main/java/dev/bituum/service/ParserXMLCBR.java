package dev.bituum.service;

import dev.bituum.model.Quotes;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface ParserXMLCBR {
    List<Quotes> parse() throws ParserConfigurationException, SAXException, IOException;
}
