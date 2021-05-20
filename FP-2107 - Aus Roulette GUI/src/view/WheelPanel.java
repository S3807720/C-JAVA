package view;
//image: http://nurseryrhymesforbabies.com/the-evolution-of-the-wheel/


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import ausroulette.model.GameEngineImpl;



public class WheelPanel extends JPanel {

	private static final long serialVersionUID = -9074640076667612250L;
	private Image wheel;
	private Image scaled;


	public WheelPanel(GameEngineImpl ge) {
		try {
			wheel = ImageIO.read(new File("images/wheel.png"));
		} catch(IOException e) {
			System.out.printf("Error opening file: %s\n%s", wheel,e);
		}

	}

	@Override
	public void invalidate() {
		super.invalidate();
		int width = getWidth();
		int height = getHeight();
		if (width > 0 && height > 0) {
			scaled = wheel.getScaledInstance(getWidth(), getHeight(),
					Image.SCALE_SMOOTH);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return wheel == null ? new Dimension(200, 200) : new Dimension(
				wheel.getWidth(this), wheel.getHeight(this));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(scaled, 0, 0, null);
	}


}
