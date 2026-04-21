package com.lxp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class QueryUtils {
    private static final Map<String, String> queries = new HashMap<>();

    static {
        loadQueries();
    }

    private static void loadQueries() {
        try {
            InputStream is = QueryUtils.class.getClassLoader().getResourceAsStream("queries.xml");
            if(is == null) {
                throw new RuntimeException("queries.xml not found");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(is);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("query");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element queryElement = (Element) nodeList.item(i);
                String id = queryElement.getAttribute("key");
                String sql = queryElement.getTextContent().trim();

                queries.put(id, sql);
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getQuery(String key) {
        return queries.get(key);
    }
}
