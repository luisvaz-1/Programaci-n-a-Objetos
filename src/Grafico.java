import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Grafico extends JFrame {
    private JPanel tableroPanel;
    private JButton[][] botones;
    private int filas;
    private int columnas;
    private Color colorFondo = Color.decode("#52ff33");
    private Color colorBoton = Color.decode("#52ff33");
    private Color colorTexto = Color.BLACK;
    private Font fuenteCartas = new Font("Arial", Font.BOLD, 24);
    private ImageIcon iconoCarta;
    private ImageIcon logoIcon;
    private JButton botonReiniciar;
    private Memorama memorama;

    public Grafico(int numPares) {
        calcularDimensiones(numPares);
        configurarVentana();
        inicializarComponentes();
    }

    private void calcularDimensiones(int numPares) {
        int totalCartas = numPares * 2;
        if (totalCartas <= 12) {
            this.filas = 3;
            this.columnas = 4;
        } else if (totalCartas <= 16) {
            this.filas = 4;
            this.columnas = 4;
        } else {
            this.filas = 4;
            this.columnas = 5;
        }
    }

    private void configurarVentana() {
        setTitle("Memorama - Juego de Memoria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(colorFondo);
        setLayout(new BorderLayout(10, 10));

        // Crear panel superior con título y botón de reinicio
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorFondo);
        
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(colorFondo);
        JLabel titulo = new JLabel("MEMORAMA");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(colorTexto);
        panelTitulo.add(titulo);
        
        // Agregar botón de reinicio
        botonReiniciar = new JButton("Reiniciar Juego");
        botonReiniciar.setFont(new Font("Arial", Font.BOLD, 14));
        botonReiniciar.setBackground(colorBoton);
        botonReiniciar.setForeground(colorTexto);
        botonReiniciar.setFocusPainted(false);
        botonReiniciar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        JPanel panelBoton = new JPanel();
        panelBoton.setBackground(colorFondo);
        panelBoton.add(botonReiniciar);
        
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperior.add(panelBoton, BorderLayout.EAST);
        
        add(panelSuperior, BorderLayout.NORTH);
    }

    private void inicializarComponentes() {
        tableroPanel = new JPanel();
        tableroPanel.setLayout(new GridLayout(filas, columnas, 10, 10));
        tableroPanel.setBackground(colorFondo);
        tableroPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        botones = new JButton[filas][columnas];
        
        // Cargar imagen del logo
        try {
            ImageIcon originalLogo = new ImageIcon("imagenes/logo.png");
            Image logoImg = originalLogo.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(logoImg);
        } catch (Exception e) {
            logoIcon = null;
        }

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                botones[i][j] = crearBoton();
                tableroPanel.add(botones[i][j]);
            }
        }

        add(tableroPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private JButton crearBoton() {
        JButton boton = new JButton();
        if (logoIcon != null) {
            boton.setIcon(logoIcon);
            boton.setHorizontalAlignment(SwingConstants.CENTER);
            boton.setVerticalAlignment(SwingConstants.CENTER);
        } else {
            boton.setBackground(colorBoton);
            boton.setForeground(colorTexto);
            boton.setText("?");
            boton.setFont(fuenteCartas);
        }
        boton.setPreferredSize(new Dimension(100, 140));
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setBorder(BorderFactory.createLineBorder(colorTexto, 2));
        return boton;
    }

    public void inicializarTablero(String[][] cartas) {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                final int fila = i;
                final int columna = j;
                botones[i][j].setName(cartas[i][j]);
            }
        }
    }

    public void revelarCarta(int fila, int columna) {
        JButton boton = botones[fila][columna];
        String nombreImagen = boton.getName();
        try {
            ImageIcon originalIcon = new ImageIcon("imagenes/" + nombreImagen + ".jpg");
            Image img = originalIcon.getImage().getScaledInstance(100, 140, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            boton.setText(nombreImagen);
            boton.setIcon(null);
        }
        boton.setBackground(colorBoton);
    }

    public void ocultarCarta(int fila, int columna) {
        JButton boton = botones[fila][columna];
        if (logoIcon != null) {
            boton.setIcon(logoIcon);
            boton.setHorizontalAlignment(SwingConstants.CENTER);
            boton.setVerticalAlignment(SwingConstants.CENTER);
        } else {
            boton.setText("?");
        }
        boton.setBackground(colorBoton);
    }

    public void marcarParEncontrado(int fila1, int columna1, int fila2, int columna2) {
        botones[fila1][columna1].setBackground(colorBoton);
        botones[fila2][columna2].setBackground(colorBoton);
    }

    public void mostrarMensajeVictoria() {
        JOptionPane.showMessageDialog(this, 
            "¡Felicitaciones! Has encontrado todos los pares.", 
            "Victoria", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void reiniciarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                ocultarCarta(i, j);
                botones[i][j].setBackground(colorBoton);
            }
        }
    }

    public void setMemorama(Memorama memorama) {
        this.memorama = memorama;
        botonReiniciar.addActionListener(e -> memorama.reiniciarJuego());
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void agregarActionListener(int fila, int columna, ActionListener listener) {
        botones[fila][columna].addActionListener(listener);
    }
}