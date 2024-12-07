import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class CardImageGenerator {
    public static void generarImagenCarta() {
        try {
            // Crear directorio de recursos si no existe
            File dir = new File("recursos");
            if (!dir.exists()) {
                dir.mkdir();
            }

            // Crear imagen de 100x140 píxeles
            BufferedImage imagen = new BufferedImage(100, 140, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = imagen.createGraphics();

            // Configurar calidad de renderizado
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            // Dibujar fondo
            GradientPaint gradiente = new GradientPaint(
                0, 0, new Color(52, 152, 219),
                100, 140, new Color(41, 128, 185)
            );
            g2d.setPaint(gradiente);
            g2d.fillRoundRect(0, 0, 100, 140, 10, 10);

            // Dibujar borde
            g2d.setColor(new Color(236, 240, 241));
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(1, 1, 97, 137, 10, 10);

            // Dibujar patrón
            g2d.setColor(new Color(236, 240, 241, 100));
            for (int i = 0; i < 5; i++) {
                g2d.drawLine(20 + i * 15, 20, 20 + i * 15, 120);
            }
            for (int i = 0; i < 7; i++) {
                g2d.drawLine(20, 20 + i * 15, 80, 20 + i * 15);
            }

            // Dibujar símbolo central
            g2d.setColor(new Color(236, 240, 241));
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fm = g2d.getFontMetrics();
            String simbolo = "?";
            int x = (100 - fm.stringWidth(simbolo)) / 2;
            int y = ((140 - fm.getHeight()) / 2) + fm.getAscent();
            g2d.drawString(simbolo, x, y);

            g2d.dispose();

            // Guardar imagen
            File outputfile = new File("recursos/carta_reverso.png");
            ImageIO.write(imagen, "png", outputfile);

        } catch (IOException e) {
            System.err.println("Error al generar la imagen de la carta: " + e.getMessage());
        }
    }
}