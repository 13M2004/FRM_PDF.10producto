package umg.progra2.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Datos SQLite
    private static final String URL = "jdbc:sqlite:C:\\Users\\Manuel Monzon\\OneDrive\\Escritorio\\SQLITE\\BasedeDatos\\db_telebot.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
