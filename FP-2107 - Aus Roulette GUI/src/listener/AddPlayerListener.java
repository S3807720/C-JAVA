package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.AddPlayerPanel;

public class AddPlayerListener implements ActionListener {
	private AddPlayerPanel panel;
	
	public AddPlayerListener(AddPlayerPanel panel) {
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.createPlayer();	
	}

}
