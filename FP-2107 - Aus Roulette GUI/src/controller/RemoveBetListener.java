package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.model.Player;
import view.RemoveBetPanel;

public class RemoveBetListener implements ActionListener {

	private RemoveBetPanel panel;
	private Player player;

	public RemoveBetListener(RemoveBetPanel removeBetPanel, Player player) {
		this.panel = removeBetPanel;
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.setBet(player);
	}

}
