package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

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
	
	  @Override
	   public Dimension getPreferredSize() {
	      return new Dimension(RECT_WIDTH + 2 * RECT_X, RECT_HEIGHT + 2 * RECT_Y);
	   }
	  
	  @Override
	  public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    int x = 0, y = 0, width, height;
	    Dimension size = this.getBounds().getSize();
	    width = (int) size.getWidth() / 3;
	    height = (int) size.getHeight() / 13;
	    g.setFont(font);
	    for (int i = 0; Wheel.NUMBER_OF_POCKETS > i;) {
	    	//0 = green
	    	if (i == 0) {
			    g.setColor(Color.GREEN);
			    g.fillRect(x, y, width*3, height);
			    y += height;
		    	drawAString(i, g, (width*3)/2, height/2);		
			    i++;
			} else {
				//column
				for (int h = 0; MAX_HEIGHT > h; h++) {
					//rows
		    		for (int w = 0; MAX_WIDTH > w; w++) {	
		    			//if within these ranges, flip colors
		    			if (i > 10 && i < 19 || i > 28) {
		    				if (i % 2 == 0) {
			    				g.setColor(Color.RED);
			    				g.fillRect(x, y, width, height);
			    				
			    			} else {
			    				g.setColor(Color.BLACK);
			    				g.fillRect(x, y, width, height);
			    			}
		    				//else even = black..
		    			} else {
		    				if (i % 2 != 0) {
			    				g.setColor(Color.RED);
			    				g.fillRect(x, y, width, height);
			    				
			    			} else {
			    				g.setColor(Color.BLACK);
			    				g.fillRect(x, y, width, height);
			    			}
		    			}
		    			//draw border and number for each tile
		    			g.setColor(Color.GRAY);
		    			g.drawRect(x, y, width+1, height+1);
		    	    	drawAString(i, g, x + width/2, y + height/2);	
		    			x += width;
		    			i++;
		    		}
		    		//reset row
		    		x = 0;
		    		y += height;
		    	}
			}
			
		}
	   
	  }
	  //helper method for drawing strings
	  private void drawAString(int i, Graphics g, int width, int height) {
		  g.setColor(Color.WHITE);
		  g.drawString(String.valueOf(i), width, height);
  	}
}