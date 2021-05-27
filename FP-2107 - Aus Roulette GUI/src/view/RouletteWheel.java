package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

//https://itqna.net/questions/7494/how-do-i-rotate-arrow-within-circle-using-java2d
//used as a base

public class RouletteWheel {

    private final int x;
    private final int y;
    private final int diameter;

	private BufferedImage wheel;


    public RouletteWheel(int x, int y, int diameter) {
        super();
        this.x = x;
        this.y = y;
        this.diameter = diameter;
		try {
			wheel = ImageIO.read(new File("images/wheel.png"));
		} catch(IOException e) {
			System.out.printf("Error opening file: %s\n%s", wheel,e);
		}
    }

    public void draw(Graphics2D g2) {
    	g2.drawImage(wheel, x, y, diameter, diameter, null);
    }
}