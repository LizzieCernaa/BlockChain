
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente extends JFrame {

   public Cliente(){
       setTitle("Cliente");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(300, 200);

       JButton enviarButton = new JButton("Enviar Mensaje");
       enviarButton.addActionListener(e -> enviarMensaje());

       getContentPane().add(enviarButton, BorderLayout.CENTER);

       setVisible(true);
   }

    private void enviarMensaje() {
        String servidor = "localhost";
        int puerto = 12345;

        try (Socket socket = new Socket(servidor, puerto)) {
            // Crear los parámetros que deseas enviar al servidor
            String parametro1 = "Hola";
            int parametro2 = 123;

            // Enviar el par de parámetros al servidor
            try (ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {
                Object[] parametros = {parametro1, parametro2};
                outputStream.writeObject(parametros);
                mostrarMensaje("Mensaje enviado al servidor: " + parametro1 + ", " + parametro2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Cliente::new);
    }


}
