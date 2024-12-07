import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class Memorama {
    private String[][] cartas;
    private Grafico grafico;
    private int numPares;
    private int paresEncontrados;
    private static final String[] IMAGENES = {
        "imagen1", "imagen2", "imagen3", "imagen4", "imagen5",
        "imagen6", "imagen7", "imagen8", "imagen9", "imagen10"
    };
    
    private boolean esperandoSegundaCarta;
    private int primeraCartaFila;
    private int primeraCartaColumna;
    private boolean bloqueado;

    public Memorama(int numPares) {
        this.numPares = numPares;
        this.grafico = new Grafico(numPares);
        this.grafico.setMemorama(this);
        this.paresEncontrados = 0;
        this.esperandoSegundaCarta = false;
        this.bloqueado = false;
        inicializarCartas();
        inicializarEventos();
    }

    private void inicializarCartas() {
        int filas = grafico.getFilas();
        int columnas = grafico.getColumnas();
        cartas = new String[filas][columnas];
        
        // Crear array con pares de imágenes
        String[] imagenesJuego = new String[numPares * 2];
        for (int i = 0; i < numPares; i++) {
            // Usar la misma imagen dos veces para formar el par
            imagenesJuego[i * 2] = IMAGENES[i];
            imagenesJuego[i * 2 + 1] = IMAGENES[i];
        }
        
        // Mezclar imágenes aleatoriamente
        Random random = new Random();
        for (int i = imagenesJuego.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            String temp = imagenesJuego[i];
            imagenesJuego[i] = imagenesJuego[j];
            imagenesJuego[j] = temp;
        }
        
        // Colocar imágenes en el tablero
        int imagenIndex = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (imagenIndex < imagenesJuego.length) {
                    cartas[i][j] = imagenesJuego[imagenIndex++];
                }
            }
        }
        
        grafico.inicializarTablero(cartas);
    }

    private void inicializarEventos() {
        for (int i = 0; i < grafico.getFilas(); i++) {
            for (int j = 0; j < grafico.getColumnas(); j++) {
                final int fila = i;
                final int columna = j;
                grafico.agregarActionListener(i, j, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        manejarClickCarta(fila, columna);
                    }
                });
            }
        }
    }

    private void manejarClickCarta(int fila, int columna) {
        if (bloqueado) {
            return;
        }

        if (!esperandoSegundaCarta) {
            // Primera carta
            primeraCartaFila = fila;
            primeraCartaColumna = columna;
            grafico.revelarCarta(fila, columna);
            esperandoSegundaCarta = true;
        } else {
            // Segunda carta
            if (fila == primeraCartaFila && columna == primeraCartaColumna) {
                return; // Misma carta, ignorar
            }

            grafico.revelarCarta(fila, columna);
            bloqueado = true;

            // Verificar si son pares
            if (cartas[primeraCartaFila][primeraCartaColumna].equals(cartas[fila][columna])) {
                // Par encontrado
                paresEncontrados++;
                grafico.marcarParEncontrado(primeraCartaFila, primeraCartaColumna, fila, columna);
                
                if (paresEncontrados == numPares) {
                    SwingUtilities.invokeLater(() -> {
                        grafico.mostrarMensajeVictoria();
                    });
                }
                
                bloqueado = false;
            } else {
                // No son pares
                Timer timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        grafico.ocultarCarta(primeraCartaFila, primeraCartaColumna);
                        grafico.ocultarCarta(fila, columna);
                        bloqueado = false;
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
            
            esperandoSegundaCarta = false;
        }
    }

    public void reiniciarJuego() {
        paresEncontrados = 0;
        esperandoSegundaCarta = false;
        bloqueado = false;
        inicializarCartas();
        grafico.reiniciarTablero();
    }

    public void jugar() {
        grafico.setVisible(true);
    }
}