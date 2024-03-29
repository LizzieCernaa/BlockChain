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
        String sql = "INSERT INTO clientes ( nombre, clave, server, balance) VALUES (?, ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(url, UserName, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, clave);
            preparedStatement.setString(3, server);
            preparedStatement.setDouble(4, balance);

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

    public boolean validarCredenciales(String nombre, String clave) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE nombre = ? AND clave = ?";

        try (
                Connection connection = DriverManager.getConnection(url, UserName, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, clave);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Si hay al menos una fila en el resultado, las credenciales son válidas
            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                return rowCount > 0;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validarNombreClienteExistente(String nombre) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE nombre = ?";

        try (
                Connection connection = DriverManager.getConnection(url, UserName, password);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, nombre);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Si hay al menos una fila en el resultado, el nombre de cliente ya existe
            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                return rowCount > 0;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ServidorModel> obtenerTodosServidores() {
        List<ServidorModel> servidores = new ArrayList<>();
        String sql = "SELECT * FROM servidores";

        try (
                Connection connection = DriverManager.getConnection(url, UserName, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                int puerto = resultSet.getInt("puerto");
                String direccionIP = resultSet.getString("direccion_ip");

                ServidorModel servidor = new ServidorModel();
                servidor.setIp(direccionIP);
                servidor.setName(nombre);
                servidor.setPort(puerto);
                servidores.add(servidor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return servidores;
    }

}
