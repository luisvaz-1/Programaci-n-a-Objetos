import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame {
    private Memorama juego;
    private Configuracion config;
    private Color colorFondo = Color.decode("#52ff33");
    private Color colorBoton = Color.decode("#52ff33");
    private Color colorTexto = Color.BLACK;
    private Font fuenteTitulo = new Font("Arial", Font.BOLD, 48);
    private Font fuenteBoton = new Font("Arial", Font.BOLD, 24);

    public Menu() {
        this.config = new Configuracion();
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Memorama - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(colorFondo);
        setLayout(new BorderLayout(20, 20));
    }

    private void inicializarComponentes() {
        // Panel del título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(colorFondo);
        JLabel titulo = new JLabel("MEMORAMA");
        titulo.setFont(fuenteTitulo);
        titulo.setForeground(colorTexto);
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(colorFondo);
        panelBotones.setLayout(new GridLayout(3, 1, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        JButton btnJugar = crearBoton("JUGAR");
        btnJugar.addActionListener(e -> iniciarJuego());

        JButton btnConfig = crearBoton("CONFIGURACIÓN");
        btnConfig.addActionListener(e -> config.mostrarConfiguracion());

        JButton btnSalir = crearBoton("SALIR");
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnJugar);
        panelBotones.add(btnConfig);
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(fuenteBoton);
        boton.setBackground(colorBoton);
        boton.setForeground(colorTexto);
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setBorder(BorderFactory.createLineBorder(colorTexto, 2));
        boton.setOpaque(true);
        
        // Efectos hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(colorBoton.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(colorBoton);
            }
        });

        return boton;
    }

    private void iniciarJuego() {
        juego = new Memorama(config.getNumPares());
        juego.jugar();
    }

    public void mostrarMenu() {
        setVisible(true);
    }
}