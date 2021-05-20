package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ausroulette.model.GameEngine;
import view.AddPlayerPanel;

public class AddPlayerButtonListener implements ActionListener {
	private GameEngine ge;
	public AddPlayerButtonListener(GameEngine ge) {
		this.ge = ge;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new AddPlayerPanel(ge);
	}

}
