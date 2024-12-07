import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class Configuracion extends JFrame {
    private int numPares;
    private Color colorFondo = Color.decode("#52ff33");
    private Color colorBoton = Color.decode("#52ff33");
    private Color colorTexto = Color.BLACK;
    private Font fuenteTitulo = new Font("Arial", Font.BOLD, 32);
    private Font fuenteNormal = new Font("Arial", Font.PLAIN, 18);
    private JLabel valorPares;
    private JSlider sliderPares;

    public Configuracion() {
        this.numPares = 6; // Valor predeterminado
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Configuración - Memorama");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(colorFondo);
        setLayout(new BorderLayout(20, 20));
    }

    private void inicializarComponentes() {
        // Panel del título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(colorFondo);
        JLabel titulo = new JLabel("CONFIGURACIÓN");
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(colorTexto);
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Configuración de pares
        JPanel panelPares = new JPanel();
        panelPares.setBackground(colorFondo);
        panelPares.setLayout(new BoxLayout(panelPares, BoxLayout.Y_AXIS));
        
        JLabel lblPares = new JLabel("Número de Pares:");
        lblPares.setFont(fuenteNormal);
        lblPares.setForeground(colorTexto);
        lblPares.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        valorPares = new JLabel(String.valueOf(numPares));
        valorPares.setFont(fuenteNormal);
        valorPares.setForeground(colorTexto);
        valorPares.setAlignmentX(Component.CENTER_ALIGNMENT);

        sliderPares = new JSlider(JSlider.HORIZONTAL, 3, 10, numPares);
        sliderPares.setBackground(colorFondo);
        sliderPares.setForeground(colorTexto);
        sliderPares.setMajorTickSpacing(1);
        sliderPares.setPaintTicks(true);
        sliderPares.setPaintLabels(true);
        sliderPares.setFont(new Font("Arial", Font.PLAIN, 12));
        sliderPares.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                numPares = sliderPares.getValue();
                valorPares.setText(String.valueOf(numPares));
            }
        });

        panelPares.add(lblPares);
        panelPares.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPares.add(valorPares);
        panelPares.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPares.add(sliderPares);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(colorFondo);
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));

        JButton btnGuardar = crearBoton("Guardar");
        btnGuardar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, 
                "Configuración guardada exitosamente", 
                "Configuración", 
                JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
        });

        JButton btnCancelar = crearBoton("Cancelar");
        btnCancelar.addActionListener(e -> setVisible(false));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelPares);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));
        panelPrincipal.add(panelBotones);

        add(panelPrincipal, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(fuenteNormal);
        boton.setBackground(colorBoton);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setBorder(BorderFactory.createLineBorder(colorTexto, 2));
        boton.setPreferredSize(new Dimension(120, 40));
        boton.setOpaque(true);

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBoton.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBoton);
            }
        });

        return boton;
    }

    public void mostrarConfiguracion() {
        setVisible(true);
    }

    public int getNumPares() {
        return numPares;
    }
}