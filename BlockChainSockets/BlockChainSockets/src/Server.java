import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Server extends JFrame {

   static JTextArea textArea;
    private JPanel panel2;
    private JButton BtnSummary;
    private JButton BtnBalance;
    private JLabel LbServerNode;
    private JLabel LbIP;
    private JLabel LbPort;
    private JLabel LbAvailable;
    private JLabel LbBlock;

    public Server() {
        setTitle("Servidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        //getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panel2);

        setVisible(true);

        // Iniciar el servidor en un hilo separado para no bloquear el EDT
        new Thread(this::iniciarServidor).start();
    }

    private void iniciarServidor() {
        int puerto = 12345;

        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            mostrarMensaje("Servidor esperando conexiones en el puerto " + puerto);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                mostrarMensaje("Cliente conectado desde " + clientSocket.getInetAddress().getHostAddress());

                // Crear hilo para manejar la conexión con el cliente
                new Thread(new ManejadorCliente(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> textArea.append(mensaje + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Server::new);
    }

}


class ManejadorCliente implements Runnable {
    private Socket clientSocket;

    public ManejadorCliente(Socket clientSocket) {
        this.clientSocket = clientSocket;
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
        Server.textArea.append(mensaje + "\n");
    }
}


