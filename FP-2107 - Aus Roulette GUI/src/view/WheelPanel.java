package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import ausroulette.model.GameEngine;
import ausroulette.model.wheel.Wheel;
import controller.NumberComboListener;

public class WheelPanel extends JPanel {

	   	private static final long serialVersionUID = 1L;
	    private double angleDegrees;
	    private JComboBox<String> wheelTurns, turnDelay;
	    private JComboBox<Integer> baseNum;
	    final private int[] WHEEL_NUMBERS = Wheel.POCKET_NUMBERS;
	    private int[] turns = { WHEEL_NUMBERS.length, ThreadLocalRandom.current().nextInt(WHEEL_NUMBERS.length, WHEEL_NUMBERS.length*3 +1),
	    		ThreadLocalRandom.current().nextInt(WHEEL_NUMBERS.length*3, WHEEL_NUMBERS.length*5 +1),
	    		ThreadLocalRandom.current().nextInt(WHEEL_NUMBERS.length*5, WHEEL_NUMBERS.length*10 +1)};
	    final private int[] WHEEL_DELAY = { 10, 100, 250, 1000 };
	    private int index = 0;
	    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	    
	    // use an array of numbers that will match the index with the
	    // pocket(eg. 0 = 0, 1 = 32? etc), and use an index to keep track of where it is, resetting at 37
	    // 
	    public WheelPanel(GameEngine ge) {
	        angleDegrees = 270;
	        defaultJComboBox();
	    }
	    
	    public void defaultJComboBox() {
	        JPanel panel = new JPanel();
	        wheelTurns = new JComboBox<String>();
	        turnDelay = new JComboBox<String>();
	        baseNum = new JComboBox<Integer>();
	        for (int nums : WHEEL_NUMBERS) {
	        	baseNum.addItem(nums);
	        }
	        wheelTurns.addItem("Quick (1 revolution)");
	        wheelTurns.addItem("Short (1 to 3 revolutions)");
	        wheelTurns.addItem("Default (3 to 5 revolutions)");
	        wheelTurns.addItem("Long (5 to 10 revolutions)");
	        turnDelay.addItem("10 ms");
	        turnDelay.addItem("100 ms");
	        turnDelay.addItem("250 ms");
	        turnDelay.addItem("1000 ms");
	        baseNum.addItemListener(new NumberComboListener(this));
	        wheelTurns.addItemListener(new NumberComboListener(this));
	        turnDelay.addItemListener(new NumberComboListener(this));
	        panel.add(wheelTurns);
	        panel.add(turnDelay);
	        panel.add(baseNum);
	        add(panel);
	    }
	    
	    public void updateTurns() {
	    	int[] temp = { WHEEL_NUMBERS.length, ThreadLocalRandom.current().nextInt(WHEEL_NUMBERS.length, WHEEL_NUMBERS.length*3 +1),
		    		ThreadLocalRandom.current().nextInt(WHEEL_NUMBERS.length*3, WHEEL_NUMBERS.length*5 +1),
		    		ThreadLocalRandom.current().nextInt(WHEEL_NUMBERS.length*5, WHEEL_NUMBERS.length*10 +1)};
	    	turns = temp;
	    }
	    
	    public void updateSettings() {
	    	new Thread(){
				public void run() {
					setBaseSpinPosition();			
				}
	        
	        }.start();
			this.pcs.firePropertyChange("number", 50, WHEEL_NUMBERS[baseNum.getSelectedIndex()]);
			this.pcs.firePropertyChange("delay", 0, WHEEL_DELAY[turnDelay.getSelectedIndex()]);
			this.pcs.firePropertyChange("spins", 0, turns[wheelTurns.getSelectedIndex()]);		
		}
	    
	    public void setBaseSpinPosition() {
	    	int startingPos = (int) baseNum.getSelectedItem();
	    	while( startingPos != WHEEL_NUMBERS[index]) {
	    		//try {
			    	spin();	
			    	if (index == WHEEL_NUMBERS.length) {
		    			index = 0;
		    		}
	    	}	    	
	    }	  
	    
	    //rotate the arrow
	    public void spin() {
	    	index ++;
	    	if (index == WHEEL_NUMBERS.length) {
				index = 0;
			}
	        angleDegrees += 9.72972972972973;
	        angleDegrees %= 360;
	        repaint();
	    }

	    @Override
	    public void paintComponent(Graphics g) {
	        Graphics2D g2 = (Graphics2D) g;
	        g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

	        super.paintComponent(g2);
	        //get window parameters
	        int widthRectangle = getWidth();
	        int heightReclangle = getHeight();

	        int x, y, diameter;
	        //resizey junk
	        if (widthRectangle <= heightReclangle) {
	            diameter = widthRectangle;
	            y = heightReclangle / 2 - diameter / 2;
	            x = 0;
	        } else {
	            diameter = heightReclangle;
	            x = widthRectangle / 2 - diameter / 2;
	            y = 0;
	        }
	        //first draw the wheel
	        RouletteWheel wheel = new RouletteWheel(x, y, diameter);
	        wheel.draw(g2);
	        //draw the arrow over the top
	        Arrow line = new Arrow(x + diameter / 2, y + diameter / 2, angleDegrees, diameter / 3, 5, 20);
	        line.draw(g2);
	    }
	    
	    public void addPropertyChangeListener(PropertyChangeListener listener) {
			this.pcs.addPropertyChangeListener(listener);
		}
		
		public void removePropertyChangeListener(PropertyChangeListener listener) {
			this.pcs.removePropertyChangeListener(listener);
		}

		
	}
