package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import view.WheelPanel;

public class NumberComboListener implements ItemListener {
	
	private WheelPanel panel;
	
	public NumberComboListener(WheelPanel panel) {
		this.panel = panel;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		new Thread(){
			public void run() {				
				panel.updateSettings();
			}
        
        }.start();
	}

}
