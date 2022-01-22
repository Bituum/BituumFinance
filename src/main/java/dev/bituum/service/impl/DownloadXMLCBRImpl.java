package dev.bituum.service.impl;

import dev.bituum.service.DownloadXMLCBR;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

@Service
@PropertySource("classpath:/config/resourcePath.properties")
public class DownloadXMLCBRImpl implements DownloadXMLCBR {
    private final String CBRPATH = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    private final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private static Logger logger = Logger.getLogger(DownloadXMLCBRImpl.class.getName());
    @Value("${path.xml}")
    private String absoluteResourceRootPath;

    @Override
    public void getTodayQuotesXML() {
        String currentDay = formatter.format(new Date());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CBRPATH + currentDay))
                .build();

        HttpResponse<String> response = null;
        try {
            logger.info("request has been send");
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.warning("request has been interrupted!");
           Thread.currentThread().interrupt();
        }
        String xmlSource = response != null ? response.body() : null;
        logger.fine("response has been successfully executed");
        createXMLFile(xmlSource);
    }

    private void createXMLFile(String xmlSource){
        try {
            logger.info("starting to create xml file");
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xmlDoc = builder.parse(new InputSource(new StringReader(xmlSource)));

            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDoc);

            StreamResult result = new StreamResult(new File(absoluteResourceRootPath));
            transformer.transform(source, result);
            logger.info("successful creating. Path => "+ absoluteResourceRootPath);
        } catch (Exception e) {
            e.printStackTrace();
            logger.warning("create xml file error");
        }
    }
}
