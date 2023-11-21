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
}
