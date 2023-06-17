import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, SQLException {
        Connection connection = DBConnection.getConnection();
        /*
        builder.length() 301_989_878
        0.2M  - 129 ms
        1M    -  413 ms
        18M   - 2200 ms
        1572M - 218124 ms
        */
        String xmlFile = "res/data-18M.xml";
        File file = new File(xmlFile);
        long size = file.length();
        System.out.println(size);
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler handler = new XMLHandler(size);
        long start = System.currentTimeMillis();

        parser.parse(new File(xmlFile), handler);
        connection.close();

        System.out.println((System.currentTimeMillis() - start) + " ms");
        connection.close();
    }
}