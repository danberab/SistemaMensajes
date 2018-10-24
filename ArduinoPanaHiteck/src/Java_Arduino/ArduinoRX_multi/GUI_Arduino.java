
package Java_Arduino.ArduinoRX_multi;
/**
 * * @author Beltran Rico Daniela
 *    Sistemas Programables
 */
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;

public class GUI_Arduino extends JFrame {
    int num, resultado;
    String ayuda;
    JLabel lblAgregarMsj, lblResultado, lblRespuesta;
    JTextField txtMensaje;
    JButton btnAgregarMsj, btnSalir,btnLuz,btnTemp,btnHum,btnBorrarRegistro;
    ArrayList<String> mensajes=new ArrayList<>();
    ImageIcon imgLuz= new ImageIcon("luz.png");
    ImageIcon imgHum = new ImageIcon("humedad.jpg");
    ImageIcon imgTemp = new ImageIcon("images.png");

    public GUI_Arduino() {
        super("Sistema notificador de mensajes en arduino");
        setLayout(new FlowLayout());
        lblAgregarMsj = new JLabel("Nuevo mensaje: ");
        lblRespuesta = new JLabel();
        txtMensaje = new JTextField(15);
        btnAgregarMsj = new JButton("Enviar");
        btnLuz = new JButton(imgLuz);
        btnHum = new JButton(imgHum);
        btnTemp = new JButton(imgTemp);
        btnAgregarMsj.setMnemonic('C');
        btnAgregarMsj.setToolTipText("Alt+c--Proceso para calcular el factorial "+
                "del numero ingresado");
        btnSalir = new JButton("Salir");
        btnSalir.setMnemonic('S');
        btnSalir.setToolTipText("Alt+s--Salir de la aplicacion");
btnBorrarRegistro = new JButton("Borrar");
        add(btnBorrarRegistro);
        add(lblAgregarMsj);
        add(txtMensaje);
        add(btnAgregarMsj);
        add(btnSalir);
        add(lblRespuesta);
        add(btnLuz);
        add(btnTemp);
        add(btnHum);
        
        String columnas[] = {"Historial de mensajes"};
        String datos[][] = {{""}};

           
        
        DefaultTableModel dtm = new DefaultTableModel(datos, columnas);

        JTable tabla = new JTable(dtm);
         JScrollPane scroll = new JScrollPane(tabla);
        getContentPane().add(scroll, BorderLayout.CENTER);
        
        btnAgregarMsj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                JOptionPane.showMessageDialog(null,"El mensaje fue enviado");
                String texto= txtMensaje.getText();
                mensajes.add(texto);
                JOptionPane.showMessageDialog(null,"El mensaje se agrego: "+mensajes.get(0));
                txtMensaje.setText("");
            }
        });
        
        btnAgregarMsj.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                btnAgregarMsj = (JButton) e.getSource();

                String[] nuevoRegistro = {mensajes.get(0)};
                dtm.addRow(nuevoRegistro);

            }
        });

          //Actualizar los campos de registro segun el renglon seleccionado
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                
                if (e.getClickCount() == 1) {
          txtMensaje.setText("" + tabla.getValueAt(tabla.getSelectedRow(), 0));

                    }
            }
        });
     
        //Borrar Fila por seleccion
        btnBorrarRegistro.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                btnBorrarRegistro = (JButton) e.getSource();

                int confirmar = JOptionPane.showConfirmDialog(null,
                        "¿Desea eliminar este registro? ");
                if (JOptionPane.OK_OPTION == confirmar) {

                    dtm.removeRow(tabla.getSelectedRow());
                    JOptionPane.showMessageDialog(null,
                            "Registro Eliminado");

                }

            }
        });
        
        
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        txtMensaje.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado;
                resultado = factorial(Integer.parseInt(txtMensaje.getText()));
                lblRespuesta.setText("El factorial del numero ingresado es: " 
                        + resultado);
            }
        });
        Manejadora objManejador = new Manejadora();
        txtMensaje.addKeyListener(objManejador);
    }
    public int factorial(int num) {
        if (num > 1) {
            return num * factorial(num - 1);
        } else {
            return 1;
        }
    }

    
    public class Manejadora implements KeyListener {
        public void keyTiped(KeyEvent e) {
            JOptionPane.showMessageDialog(null, e.getKeyText(e.getKeyChar()));
        }
        @Override
        public void keyPressed(KeyEvent e) {
        }
        @Override
        public void keyReleased(KeyEvent e) {
        }
        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
    
    public static void main(String[] args) {
        GUI_Arduino objFact=new GUI_Arduino();
        objFact.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        objFact.setSize(500,400);
        objFact.setVisible(true);
        objFact.setResizable(false);
        
    }
}
