
import javax.swing.*;
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
    private String ip;
    private int puerto;

    public Cliente(String user, String server, double balance, int port){
       setTitle("Cliente");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);

       getContentPane().add(panel1);
       setVisible(true);
        this.puerto = port;
        txtUsuario.setText(user);
        txtBalance.setText(String.valueOf(balance));
        cmbServer.addItem(server);

        txtSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                //this.sendTransaction();
                txtAmount.setText("");
                txtSend.setText("");

            }
        });
    }

    private void enviarMensaje() {
        String servidor = "127.0.0.1";


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


    /*public boolean sendTransaction()
    {
        String sNode= this.nodeData.getNodeName();
        String sReceiver= this.txtSend.getText().trim().toUpperCase();
        Double dAmount= Double.parseDouble(this.txtAmount.getText());

        int iserver= this.jComboBox1.getSelectedIndex();

        if (dAmount<=this.dCurrentBalance)
        {
            this.jLabel4.setText("Current Balance: ");
            this.dCurrentBalane-=dAmount;

            try
            {
                sNode= this.oCifrado.encriptar(sNode);
                sReceiiver= this.oCifrado.encriptar(sReceiver);

                Block blk= new Block();
                blk.setTransaction(sNode,dAmount,sReceiver);

                Socket socket = new Socket(
                        this.aServers.get(iserver).getIPAddress(),
                        this.aServers.get(iserver).getSocketNum());
                ObjectOutputStream oss =
                        new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(blk);
                socket.close();
                this.jLabel4.setText("Current Bolance:");
                this.lBalance.setText("$ "+Double.toString(this.dCurrentBalance));
                return true;
            }
            catch (Exception e)
            {
                this.dCurrentBalance+=dAmount;
                this.jLabel5.setText(e.toString());
            }
        }
        else this.jLabel4.setText("-Insufficient Balance: ");
        return false;
    }*/

}
