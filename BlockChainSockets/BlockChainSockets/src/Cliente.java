
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente extends JFrame {

    private JPanel panel1;
    private JTextField textField1;
    private JTextField txtAmount;
    private JComboBox cmbServer;
    private JButton txtSend;
    private JLabel txtUsuario;
    private JLabel txtBalance;
    private JPasswordField txtPassword;
    private JLabel LbPass;
    private JLabel txtIpPuerto;
    private String ip;
    private int puerto;

    public Cliente(String user, String server, double balance, String ip, int port){
       setTitle("Cliente");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

       getContentPane().add(panel1);
       setVisible(true);
        this.puerto = port;
        this.ip = ip;
        txtUsuario.setText(user);
        txtBalance.setText(String.valueOf(balance));
        txtIpPuerto.setText(this.ip+"/"+this.puerto);
        cmbServer.addItem(server);

        txtSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var sendt = textField1.getText();
                DbConnection con = new DbConnection();

                var resultado = con.validarNombreClienteExistente(sendt);
                if (resultado)
                    JOptionPane.showMessageDialog(null, "Usurio Existente");
                else
                    JOptionPane.showMessageDialog(null, "Error, usuario no existe");

                var usuario = txtUsuario.getText();
                var password = txtPassword.getText();

                var cifrador = new Cifrado("NADA12345");
                String claveCifrada = null;
                try {
                    claveCifrada = cifrador.encriptar(password.toString());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                var resultados = con.validarCredenciales(usuario, claveCifrada);

                if (resultados)
                    JOptionPane.showMessageDialog(null, "Usurio y clave validado");
                else
                    JOptionPane.showMessageDialog(null, "Error, usuario y/o clase no valido");

                //
            }
        });
    }

    private void enviarMensaje() {
        String servidor = this.ip;


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

}
