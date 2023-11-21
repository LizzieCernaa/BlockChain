import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockChainManage extends JFrame {
    private JPanel panel1;
    private JTextField txtServerName;
    private JTextField txtIP;
    private JTextField txtPort;
    private JButton newServerButton;
    private JTextField txtClientName;
    private JTextField txtClientBalance;
    private JButton newWalletButton;
    private JList list1;
    private JList list2;
    private JTextField txtClienteServer;
    private JTextField txtPassword;


    public BlockChainManage(){
        setTitle("Servidor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 600);
        getContentPane().add(panel1);
        setVisible(true);
        newServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DbConnection con = new DbConnection();
                var name= txtServerName.getText();
                var ip = txtIP.getText();
                var port = Integer.parseInt(txtPort.getText());
                var resultado = con.guardarServidor(name, ip, port);
                if (resultado)
                    JOptionPane.showMessageDialog(null, "servidor creado");
                else
                    JOptionPane.showMessageDialog(null, "Error creando el servidor");

                txtServerName.setText("");
                txtIP.setText("");
                txtPort.setText("");
                var server = new Server(name, ip, port);
            }
        });
        newWalletButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var name = txtClientName.getText();
                var balance = Double.parseDouble(txtClientBalance.getText());
                var clave = txtPassword.getText();
                var server = txtClienteServer.getText();
                var cifrador = new Cifrado("NADA12345");
                String claveCifrada = null;
                try {
                    claveCifrada = cifrador.encriptar(clave.toString());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                var con = new DbConnection();

                var resultado = con.CrearWallet(name,claveCifrada,server, balance);
                if (resultado)
                    JOptionPane.showMessageDialog(null, "wallet creada");
                else
                    JOptionPane.showMessageDialog(null, "Error creando wallet");

                var cliente = new Cliente(name, server, balance,7001);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BlockChainManage::new);
    }

}
