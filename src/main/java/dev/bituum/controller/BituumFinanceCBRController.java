package dev.bituum.controller;

import dev.bituum.model.Quotes;
import dev.bituum.service.CBRService;
import dev.bituum.service.DownloadXMLCBR;
import dev.bituum.service.ParserXMLCBR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
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

    @Autowired
    private CBRService cbrService;

    private List<Quotes> quotesList;

    private void initService(){
        downloadXMLCBR.getTodayQuotesXML();
        try {
            quotesList = parserXMLCBR.parse();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/get/{CHRCD}")
    public Quotes getQuoteByCharCode(@PathVariable("CHRCD") String charCode){
        initService();
        return cbrService.getOneByCharCode(quotesList, charCode);
    }

    @GetMapping("/cmp/{CHRCD1}/{CHRCD2}")
    public int compareTwoQuotes(@PathVariable("CHRCD1") String first, @PathVariable("CHRCD2") String second){
        initService();
        return cbrService.compareTwoQuotes(quotesList, first, second);
    }
    @GetMapping("/mlt/{amount}/to/{CHRCD}")
    public double multiplyQuote(@PathVariable("amount") int amount, @PathVariable("CHRCD") String charCode){
        initService();
        return cbrService.multiplyQuotes(quotesList, charCode, amount);
    }
}
