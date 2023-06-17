import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XMLHandler extends DefaultHandler {
    StringBuilder builder = new StringBuilder();
    private long size;

    XMLHandler(long size) throws SQLException {
        DBConnection.getConnection();
        this.size = size;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("voter")) {
            String name = attributes.getValue("name");
            String birthDay = attributes.getValue("birthDay");
            birthDay = birthDay.replace('.', '-');
            builder.append(builder.length() == 0 ? "" : ", ")
                    .append("(").append("'").append(name).append("'")
                    .append(", ").append("'").append(birthDay).append("'")
                    .append(", ");
        }
        if (qName.equals("visit")) {
            String station = attributes.getValue("station");
            String time = attributes.getValue("time");
            time = time.replace('.', '-');
            builder.append(station)
                    .append(", ").append("'").append(time).append("'")
                    .append(")")
                    .append("\n");
            if (builder.length() > size/30) {
                DBConnection.pushInDB(builder.toString());
                builder.delete(0, builder.length());
            }
        }
    }

    public String getResult() {
        return builder.toString();
    }

}