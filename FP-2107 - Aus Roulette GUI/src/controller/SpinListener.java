package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.Toolbar;

public class SpinListener implements ActionListener {

	private Toolbar toolbar;

	public SpinListener(Toolbar toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		toolbar.startSpin();
	}

}
