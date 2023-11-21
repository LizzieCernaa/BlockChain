import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Server extends JFrame {

    private JPanel panel2;
    private JButton BtnSummary;
    private JButton BtnBalance;
    private JLabel LbServerNode;
    private JLabel LbIP;
    private JLabel LbPort;
    private JLabel LbAvailable;
    private JLabel LbBlock;
    private JPanel JOptionPane;
    private JLabel txtName;
    private JLabel txtIP;
    private JLabel txtPort;
    private JTextArea txtMensajes;

    private String IP;

    private int Port;


    public Server(String Name, String IP, int Port) {
        setTitle("Servidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        txtName.setText(Name);
        txtIP.setText(IP);
        txtPort.setText(String.valueOf(Port));
        this.IP=IP;
        this.Port =Port;
        txtMensajes.setEditable(false);
        getContentPane().add(panel2);
        setVisible(true);

        // Iniciar el servidor en un hilo separado para no bloquear el form
        new Thread(this::iniciarServidor).start();
    }

    private void iniciarServidor() {


        try (ServerSocket serverSocket = new ServerSocket(this.Port)) {
            mostrarMensaje("Servidor esperando conexiones en el puerto " + this.Port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                mostrarMensaje("Cliente conectado desde " + clientSocket.getInetAddress().getHostAddress());

                // Crear hilo para manejar la conexión con el cliente
                new Thread(new ManejadorCliente(clientSocket, this)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> txtMensajes.append(mensaje + "\n"));
    }


}


class ManejadorCliente implements Runnable {
    private Socket clientSocket;
    private Server server;

    public ManejadorCliente(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try (ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream())) {
            // Recibir el par de parámetros desde el cliente
            Object[] parametros = (Object[]) inputStream.readObject();

            // Procesar los parámetros (puedes hacer lo que necesites con ellos)
            mostrarMensaje("Mensaje recibido desde el cliente: " + parametros[0] + ", " + parametros[1]);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String mensaje) {
        server.mostrarMensaje(mensaje + "\n");
    }
}


