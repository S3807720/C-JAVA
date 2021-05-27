package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.model.Player;
import view.RemoveBetPanel;

public class RemovePlayerMenuListener implements ActionListener {

	private Player player;
	private RemoveBetPanel panel;

	public RemovePlayerMenuListener(Player player, RemoveBetPanel removeBetPanel) {
		this.player = player;
		this.panel = removeBetPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.betMenu(player);
	}

}
