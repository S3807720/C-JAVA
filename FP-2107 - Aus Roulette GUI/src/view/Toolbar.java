package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;

import ausroulette.model.GameEngineImpl;
import controller.SpinListener;
import model.ToolbarItems;

public class Toolbar extends JToolBar implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private int ticks = 37, delay = 10, startingNumber = 0;
	private WheelPanel wheelPanel;
	private GameEngineImpl ge;
	
	
	public Toolbar(GameEngineImpl ge, WheelPanel wheelPanel) {
		this.ge = ge;
		this.wheelPanel = wheelPanel;
		setLayout(new GridLayout());
		wheelPanel.addPropertyChangeListener(this);
		Border border = BorderFactory.createLineBorder(Color.DARK_GRAY);
		for (ToolbarItems item : ToolbarItems.values()) {
			char key = item.toString().toLowerCase().charAt(0);

			AbstractButton button = new JButton(item.toString());
			button.setPreferredSize(new Dimension(60, 60));
			button.setBorder(border);

			button.setMnemonic(key);

			button.addActionListener(new SpinListener(this));
			add(button);


		}

	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().contentEquals("number")) {
			this.startingNumber = (int) evt.getNewValue();
		} else if (evt.getPropertyName().contentEquals("delay")) {
			this.delay = (int) evt.getNewValue();
		} else if (evt.getPropertyName().contentEquals("spins")) {
			this.ticks = (int) evt.getNewValue();
		}
	}
	public void startSpin() {
		new Thread(){
			public void run() {
				//update the random turns before spinnning
				wheelPanel.updateTurns();
				wheelPanel.updateSettings();
				ge.spinToWin(ticks, delay, startingNumber);
				
			}      
        }.start();
		
	}
}
