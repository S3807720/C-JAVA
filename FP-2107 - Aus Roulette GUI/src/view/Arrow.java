package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;

// https://itqna.net/questions/7494/how-do-i-rotate-arrow-within-circle-using-java2d
// used as a base

public class Arrow {

    private final int x;
    private final int y;
    private final int endX;
    private final int endY;
    private final double angleRadians;
    private final Color color = Color.BLACK;
    private final int thickness;
    private final double scale;

    private static final int TRIANGLE_LENGTH = 2;
    private static final Polygon ARROW_HEAD = new Polygon();

    static {
        ARROW_HEAD.addPoint(TRIANGLE_LENGTH, 0);
        ARROW_HEAD.addPoint(0, -TRIANGLE_LENGTH / 2);
        ARROW_HEAD.addPoint(0, TRIANGLE_LENGTH / 2);
    }

    public Arrow(int x, int y, double angleDegrees, int length, int thickness, int headSize) {
        super();
        this.x = x;
        this.y = y;
        this.thickness = thickness;

        // Converte o ângulo para radianos.
        this.angleRadians = Math.toRadians(angleDegrees);

        // Calcula a escala a ser aplicada ao desenhar a ponta.
        this.scale = headSize / TRIANGLE_LENGTH;

        // Calcula a posição final da linha de acordo com o ângulo e com o
        // comprimento. Corta do comprimento o tamanho da ponta.
        this.endX = (int) (x + (length - headSize) * Math.cos(angleRadians));
        this.endY = (int) (y + (length - headSize) * Math.sin(angleRadians));
    }

    public void draw(Graphics2D g2) {
        // Define a cor e a espessura da linha.
        g2.setColor(color);
        g2.setStroke(new BasicStroke(thickness));

        // Desenha a linha.
        g2.drawLine(x, y, endX, endY);

        // Obtém o AffineTransform original.
        AffineTransform tx1 = g2.getTransform();

        // Cria uma cópia do AffineTransform.
        AffineTransform tx2 = (AffineTransform) tx1.clone();

        // Translada e rotaciona o novo AffineTransform.
        tx2.translate(endX, endY);
        tx2.scale(scale, scale);
        tx2.rotate(angleRadians);

        // Desenha a ponta com o AffineTransform transladado e rotacionado.
        g2.setTransform(tx2);
        g2.fill(ARROW_HEAD);

        // Restaura o AffineTransform original.
        g2.setTransform(tx1);
    }
}
