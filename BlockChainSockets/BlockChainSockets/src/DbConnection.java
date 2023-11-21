import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnection {
    private String url = "jdbc:oracle:thin:@127.0.0.1:1521:ORCLCDB";
    private String UserName = "SYSTEM";
    private String password = "l+uTZ2g2Eo4=1";
    private String error;
    private Connection conn;
    public boolean Conectar()
    {
      try {
        conn = DriverManager.getConnection(url, UserName, password);
        return true;
        }catch (Exception ex)
        {
        error= ex.getMessage();
        return false;
        }
    }

    public String getError() {
        return error;
    }

    public boolean CrearWallet(String nombre, String clave, String server, double balance) {
        String sql = "INSERT INTO clientes ( nombre, clave, server, balance) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(url, UserName, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, clave);
            preparedStatement.setString(3, server);
            preparedStatement.setDouble(4, balance);

            preparedStatement.executeUpdate();
            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean guardarServidor(String nombre, String direccionIP, int puerto) {
        String sql = "INSERT INTO servidores (nombre, puerto, direccion_ip) VALUES ( ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(url, UserName, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, puerto);
            preparedStatement.setString(3, direccionIP);

            int filasAfectadas = preparedStatement.executeUpdate();
            return filasAfectadas > 0; // Retorna true si al menos una fila fue afectada
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




}
