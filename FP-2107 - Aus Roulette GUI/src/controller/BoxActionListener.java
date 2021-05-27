package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import view.AddBetPanel;

public class BoxActionListener implements ActionListener {
	AddBetPanel panel;
	JComboBox<Integer> num;
	
	public BoxActionListener(AddBetPanel panel, JComboBox<Integer> box) {
		this.panel = panel;
		this.num = box;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		panel.createTwoSplitBox((int) num.getSelectedItem());
	}

}
