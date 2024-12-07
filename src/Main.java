import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Establecer el look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Generar la imagen de la carta
        CardImageGenerator.generarImagenCarta();

        // Iniciar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Menu menu = new Menu();
                menu.mostrarMenu();
            }
        });
    }
}