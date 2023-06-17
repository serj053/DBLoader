import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;
    private static String url = "jdbc:mysql://localhost:3306/voters";
    private static String user = "root";
    private static String pass = "3141";

    //создаем соединение и перезаписываем структуру таблицы
    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, pass);
        connection.createStatement().execute("DROP TABLE IF EXISTS about_voters");
        connection.createStatement().execute("CREATE TABLE about_voters(" +
                "id INT NOT NULL AUTO_INCREMENT, " +
                "name TINYTEXT NOT NULL, " +
                "birthDay Date NOT NULL, " +
                "station INT NOT NULL, " +
                "time DATETIME NOT NULL, " +
                "PRIMARY KEY(id))"
        );
        DBConnection.connection = connection;
        return connection;
    }

    public static void pushInDB(String sql)  {
        String insert = "INSERT INTO about_voters(name, birthDay, station, time) " +
                "VALUES" + sql +";";
        try {
            DBConnection.connection.createStatement().execute(insert);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
