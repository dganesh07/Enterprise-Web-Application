import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;



public class XMLlist extends DefaultHandler {
    Product pr;
    List<Product> prs;
    String productXmlFileName;
    String elementValueRead;
    HttpSession session;


    public XMLlist(String productXmlFileName) {
        this.productXmlFileName = productXmlFileName;
        prs = new ArrayList<Product>();
        parseDocument();
        System.out.println(prs.size());
        //prettyPrint();
        //session.setAttribute("productlist", prs);
    }

    public List<Product> getProducts() {
        return prs;
    }

    private void parseDocument() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(productXmlFileName, this);
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfig error");
        } catch (SAXException e) {
            System.out.println("SAXException : xml not well formed");
        } catch (IOException e) {
            System.out.println("IO error");
            e.printStackTrace();
        }
    }

    /*private void prettyPrint() {

            return prs;
        }*/




    @Override
    public void startElement(String str1, String str2, String elementName, Attributes attributes) throws SAXException {

        if (elementName.equalsIgnoreCase("product")) {
            pr = new Product();
            pr.setId(attributes.getValue("id"));
            pr.setRetailer(attributes.getValue("retailer"));
        }

    }

    @Override
    public void endElement(String str1, String str2, String element) throws SAXException {

        if (element.equals("product")) {
            prs.add(pr);
            return;
        }
        if (element.equalsIgnoreCase("image")) {
            pr.setImage(elementValueRead);
            return;
        }
        if (element.equalsIgnoreCase("type")) {
            pr.setType(elementValueRead);
            return;
        }

        if (element.equalsIgnoreCase("name")) {
            pr.setName(elementValueRead);
            return;
        }
       if (element.equalsIgnoreCase("condition")) {                //changed
            pr.setCondition(elementValueRead);
            return;
        }
         if (element.equalsIgnoreCase("retailer")) {                //changed
            pr.setRetailer(elementValueRead);
            return;
        }
        if (element.equalsIgnoreCase("accessory")) {
            pr.getAccessories().add(elementValueRead);
            return;
        }
        if (element.equalsIgnoreCase("price")) {
            pr.setPrice(Integer.parseInt(elementValueRead));
            return;
        }

    }

    @Override
    public void characters(char[] content, int begin, int end) throws SAXException {
        elementValueRead = new String(content, begin, end);
    }




}
