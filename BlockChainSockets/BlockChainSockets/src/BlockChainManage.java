import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockChainManage extends JFrame {
    private JPanel panel1;
    private JTextField txtServerName;
    private JTextField txtIP;
    private JTextField txtPort;
    private JButton newServerButton;
    private JTextField textField5;
    private JTextField textField6;
    private JComboBox comboBox1;
    private JButton newWalletButton;
    private JList list1;
    private JList list2;


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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BlockChainManage::new);
    }
}
