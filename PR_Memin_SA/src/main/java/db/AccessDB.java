package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccessDB {

    private AccessDB() {
    }

    public static Connection getConnection() throws SQLException {
        Connection cn = null;
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String DB = "db_Memin_mf";
        String server = "localhost:14033";
        String urlDB = "jdbc:sqlserver://"+ server +";databaseName="+ DB +";encrypt=True;TrustServerCertificate=True;loginTimeout=30;";
        String user = "sa";
        String pass = "MyPassword2023";
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
            cn = DriverManager.getConnection(urlDB, user, pass);
        } catch (SQLException e) {
            throw e;
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver de la base de datos.");
        } catch (Exception e) {
            throw new SQLException("No se puede establecer la conexion con la BD:");
        }
        return cn;
    }

}
