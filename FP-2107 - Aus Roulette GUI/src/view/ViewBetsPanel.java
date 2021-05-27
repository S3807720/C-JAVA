package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.model.bet.Bet;
import controller.CloseActionListener;
import controller.ViewChangeListener;

public class ViewBetsPanel extends JFrame{
	private JPanel betDisplayList;
	private JFrame frame;
	JComboBox<String> playerBox = new JComboBox<String>();
	private static final long serialVersionUID = 6376818431792548673L;
	Collection<Player> PLAYER_LIST;
	
	public ViewBetsPanel(GameEngine ge) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("View bets");
		CloseActionListener.removeMinMaxClose(frame);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(250, 220);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		betDisplayList = new JPanel();		
		betDisplayList.setLayout(new GridLayout(0,1));
		//create a button named after each player..
		PLAYER_LIST = ge.getAllPlayers();
		try {
			playerBox.addItem("");
			playerBox.addItemListener( new ViewChangeListener(this));
			for (Player play : PLAYER_LIST) {
				if (!play.getBets().isEmpty()) {
					playerBox.addItem(play.getName());
				}
			}
		}catch (NullPointerException n) {
			frame.add("Center", new JLabel(" No players at the moment."));
		}
		JButton button = new JButton("Close");
		button.addActionListener(new CloseActionListener(frame));
		frame.add("North", playerBox);
		frame.add("Center", createScrollFrame());
		frame.add("South", button);
	}
	
	public void fillBets() {
		String name = playerBox.getSelectedItem().toString();
		Player player = null;
		for (Player play : PLAYER_LIST) {
			if (play.getName() == name) { 
				player = play;
			}
		}
		betDisplayList.removeAll();
		if (player != null) {
			for (Bet bets : player.getBets()) {
				betDisplayList.add(new JLabel(bets.toString()));
			}
		}
		betDisplayList.revalidate();
	}
	
	public JScrollPane createScrollFrame() {
		JScrollPane scrollFrame=new JScrollPane(betDisplayList, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollFrame.getVerticalScrollBar().setUnitIncrement(10);
		scrollFrame.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		return scrollFrame;
	}
}
