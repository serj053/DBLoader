import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;
public class XMLHandler extends DefaultHandler {
    StringBuilder builder = new StringBuilder();
    private final long size;

    XMLHandler(long size) throws SQLException {
        DBConnection.getConnection();
        this.size = size;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes){
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