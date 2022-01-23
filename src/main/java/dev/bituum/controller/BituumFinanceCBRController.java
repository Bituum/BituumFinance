package dev.bituum.controller;

import dev.bituum.model.Quotes;
import dev.bituum.service.DownloadXMLCBR;
import dev.bituum.service.ParserXMLCBR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/cbr")
public class BituumFinanceCBRController {
    private static Logger logger = Logger.getLogger(BituumFinanceCBRController.class.getName());
    @Autowired
    private DownloadXMLCBR downloadXMLCBR;

    @Autowired
    private ParserXMLCBR parserXMLCBR;

    private List<Quotes> quotesList;

    @GetMapping("/all")
    public void getXML(){
        downloadXMLCBR.getTodayQuotesXML();
    }

    //parsing xml file
    @GetMapping("/parse")
    public void parseXML(){
        try {
            quotesList = parserXMLCBR.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO CACHED CHECK
}
