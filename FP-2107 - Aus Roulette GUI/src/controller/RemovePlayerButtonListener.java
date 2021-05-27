package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.model.GameEngine;
import view.RemovePlayerPanel;

public class RemovePlayerButtonListener implements ActionListener {
	private GameEngine ge;
	
	public RemovePlayerButtonListener(GameEngine ge) {
		this.ge = ge;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new RemovePlayerPanel(ge);
	}

}
