package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.model.Player;

public class ToolbarButtonListener implements ActionListener {
	Player player;
	
	public ToolbarButtonListener(Player player) {
		this.player = player;
	}
	
	public void actionPerformed(ActionEvent e) {
		//editLabel(player);
	}
}
