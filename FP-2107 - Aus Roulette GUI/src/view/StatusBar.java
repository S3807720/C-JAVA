package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import ausroulette.model.GameEngineImpl;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements PropertyChangeListener {

	private JLabel statusFirst, statusSecond;

	public StatusBar(GameEngineImpl ge) {
		setBackground(Color.PINK);
		setOpaque(true);

		setLayout(new GridLayout());

		Border border = BorderFactory.createLineBorder(Color.BLACK);

		statusFirst = new JLabel(" ", SwingConstants.LEFT);
		statusFirst.setBorder(border);
		statusFirst.setHorizontalAlignment(JLabel.CENTER);
		statusSecond = new JLabel(" ", SwingConstants.LEFT);
		statusSecond.setBorder(border);
		statusSecond.setHorizontalAlignment(JLabel.CENTER);
		add(statusFirst);
		add(statusSecond);

	}

	public void updateText(String thing) {
		statusFirst.setText(thing);
	}

	public void updateTwoText(String thing) {
		statusSecond.setText(thing);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		statusFirst.setText(evt.getNewValue() + " selected.");
	}

}
