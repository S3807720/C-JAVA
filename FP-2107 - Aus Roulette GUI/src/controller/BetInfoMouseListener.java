package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ausroulette.model.Player;
import ausroulette.model.bet.Bet;

public class BetInfoMouseListener implements MouseListener {
	private Player player;
	private JFrame frame;
	private JPanel panel;

	public BetInfoMouseListener(Player player) {
		this.player = player;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame(String.format("%s current bets", player.getName()));
		CloseActionListener.removeMinMaxClose(frame);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setMinimumSize(new Dimension(250, 250));		
		frame.setLayout(new BorderLayout());
		panel = new JPanel();
		frame.setVisible(true);
		JLabel label; 
		Collection<Bet> bets = player.getBets();

		String betString = "<html>";
		if(bets.isEmpty()) {
			betString += player.getName() + " has no bets.";
		}
		for (Bet bet : bets) {
			betString += bet.toString() +"<br>";
		}

		betString += "</html>";
		label = new JLabel(betString);
		panel.add(label, BorderLayout.WEST);

		JButton button = new JButton("Close");
		button.addActionListener(new CloseActionListener(frame));

		JScrollPane scrollFrame=new JScrollPane(panel);
		scrollFrame.getVerticalScrollBar().setUnitIncrement(10);
		scrollFrame.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

		//frame.add(panel);
		frame.add(scrollFrame, BorderLayout.CENTER);
		frame.add(button, BorderLayout.SOUTH);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}


}



