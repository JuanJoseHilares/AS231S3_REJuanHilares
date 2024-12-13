package db;

import java.sql.Connection;

public class ConecctionTest {
    public static void main(String[] args) {
        try {
            Connection cn = AccessDB.getConnection();
            if (cn != null) {
                System.out.println("Conexión exitosa con la base de datos");
                cn.close();
            } else {
                System.out.println("No se pudo establecer la conexión con la base de datos Futora.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
