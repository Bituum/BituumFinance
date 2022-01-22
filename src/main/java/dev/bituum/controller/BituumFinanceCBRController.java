package dev.bituum.controller;

import dev.bituum.service.DownloadXMLCBR;
import dev.bituum.service.ParserXMLCBR;
import dev.bituum.service.impl.DownloadXMLCBRImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/sbr")
public class BituumFinanceCBRController {
    private static Logger logger = Logger.getLogger(BituumFinanceCBRController.class.getName());
    @Autowired
    private DownloadXMLCBR downloadXMLCBR;

    @Autowired
    private ParserXMLCBR parserXMLCBR;

    @GetMapping("/all")
    public void getXML(){
        downloadXMLCBR.getTodayQuotesXML();
    }

    @GetMapping("/parse")
    public void parseXML(){
        try {
            parserXMLCBR.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
