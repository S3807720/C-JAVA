package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.model.Player;
import ausroulette.model.bet.BetType;
import view.AddBetPanel;

public class BetTypeListener implements ActionListener {
	private BetType type;
	private AddBetPanel panel;
	private Player player;
	
	public BetTypeListener(Player player, BetType type, AddBetPanel addBetPanel) {
		this.type = type;
		this.panel = addBetPanel;
		this.player = player;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.setBetType(type);
		if (type == BetType.BLACK || type == BetType.RED) { 
			panel.colorBets(player);
		} else {
			panel.numBetMenu(player);
		}
	}

}
