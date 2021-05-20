package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import ausroulette.model.GameEngineImpl;
import ausroulette.model.wheel.Wheel;

public class BettingTablePanel extends JPanel {

	private static final long serialVersionUID = -3190907552945995881L;
	private Font font = new Font("Serif", Font.BOLD, 12);

	private final int RECT_X = 20;
	private final int RECT_Y = 10;
	private final int RECT_WIDTH = 120;
	private final int RECT_HEIGHT = RECT_WIDTH / 2;
	private final int MAX_WIDTH = 3;
	private final int MAX_HEIGHT = 12;

	public BettingTablePanel(GameEngineImpl ge) {
		
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int x = 0, y = 0, width, height;
		Dimension size = this.getBounds().getSize();
		width = (int) size.getWidth() / 4;
		height = (int) size.getHeight() / 13;
		g.setFont(font);
		//manually draw the red/black side bar..
		fillRect(g, Color.BLACK, x, y, width, (int) size.getHeight() / 2);
		drawAString("Black", g, x + width/6, y + height*3);
		y += (int) size.getHeight()/2;
		fillRect(g, Color.RED, x, y, width, (int) size.getHeight() / 2);
		drawAString("Red",g, x + width/4, y + height*3);
		x += width;
		y = 0;
		//loop through pockets
		for (int i = 0; Wheel.NUMBER_OF_POCKETS > i;) {
			//0 = green, custom width etc
			if (i == 0) {
				fillRect(g, Color.GREEN.darker(), x, y, width*3, height);
				y += height;
				drawAString(i, g, (width*5)/2, height/2);		
				i++;
			} else {
				//column
				for (int h = 0; MAX_HEIGHT > h; h++) {
					//rows
					for (int w = 0; MAX_WIDTH > w; w++) {	
						//if within these ranges, flip colors
						if (i > 10 && i < 19 || i > 28) {
							if (i % 2 == 0) {
								fillRect(g, Color.RED, x, y, width, height);
							} else {
								fillRect(g, Color.BLACK, x, y, width, height);	
							}
							//else even = black..
						} else {
							if (i % 2 != 0) {
								fillRect(g, Color.RED, x, y, width, height);
							} else {
								fillRect(g, Color.BLACK, x, y, width, height);
							}
						}

						drawAString(i, g, x + width/2, y + height/2);	
						x += width;
						i++;
					}
					//reset row & increment height
					x = width;
					y += height;
				}
			}

		}

	}
	//helper method for filling the tiles
	public void fillRect(Graphics g, Color color, int x, int y, int width, int height) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
		//draw border and number for each tile
		g.setColor(Color.GRAY);
		g.drawRect(x, y, width+1, height+1);
	}
	
	//helper method for drawing nums
	private void drawAString(int i, Graphics g, int width, int height) {
		drawAString(String.valueOf(i), g, width, height);
	}
	//helper method for drawing strings
	private void drawAString(String string, Graphics g, int width, int height) {
		g.setColor(Color.WHITE);
		g.drawString(string, width, height);
	}
}