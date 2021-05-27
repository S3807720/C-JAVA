package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import ausroulette.model.Player;
import view.RemovePlayerPanel;

public class RemovePlayerActionListener implements ActionListener {
	private RemovePlayerPanel panel;
	private Player player;
 	
	public RemovePlayerActionListener(RemovePlayerPanel removePlayerPanel, Player player) {
		this.panel = removePlayerPanel;
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			int n = JOptionPane.showConfirmDialog(null, (String.format("Are you sure you want to remove %s?", player.getName())), 
					null, JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				panel.removePlayer(player);
				
			}
	}

}
