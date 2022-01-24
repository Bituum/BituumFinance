package dev.bituum.util;

import dev.bituum.model.Quotes;
import lombok.Getter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
@Getter
public class CBRHandler extends DefaultHandler {
    private String id;
    private int NumCode;
    private String CharCode;
    private int Nominal;
    private String Name;
    private Double Value;
    private String elName;
    private List<Quotes> quotesList;

    public CBRHandler(List<Quotes> quotesList) {
        this.quotesList = quotesList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        elName = qName;
        if(qName.equals("Valute")){
            id = attributes.getValue("ID");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String information = new String(ch, start, length);

        information = information.replace("\n", "").trim();
        information = information.replace(",", ".").trim();
        if (!information.isEmpty()) {
            if (elName.equals("NumCode"))
                NumCode = Integer.parseInt(information);
            if (elName.equals("CharCode"))
                CharCode = information;
            if (elName.equals("Nominal"))
                Nominal = Integer.parseInt(information);
            if (elName.equals("Name"))
                Name = information;
            if (elName.equals("Value"))
                Value = Double.parseDouble(information);
            if(Value != null){
                quotesList.add(Quotes.builder()
                        .id(id)
                        .NumCode(NumCode)
                        .CharCode(CharCode)
                        .Nominal(Nominal)
                        .Name(Name)
                        .Nominal(Nominal)
                        .Value(Value)
                        .build());
                Value = null;
            }
        }
    }

}

