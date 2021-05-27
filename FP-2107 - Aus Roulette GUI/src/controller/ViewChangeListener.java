package controller;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import view.ViewBetsPanel;

public class ViewChangeListener implements ItemListener {

	private ViewBetsPanel panel;

	public ViewChangeListener(ViewBetsPanel viewBetsPanel) {
		this.panel = viewBetsPanel;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		panel.fillBets();
	}

}
