package view;

import java.awt.GridLayout;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import controller.CloseActionListener;
import controller.RemovePlayerActionListener;

public class RemovePlayerPanel extends JFrame {
	private JPanel panel;
	private JFrame frame;
	private ButtonGroup icons;
	private GameEngine ge;
	private Map<String, AbstractButton> buttons = new HashMap<String, AbstractButton>();
	Collection<Player> PLAYER_LIST;
	private static final long serialVersionUID = 8152431565274127120L;

	public RemovePlayerPanel(GameEngine ge) {
		//general frame look
		this.ge = ge;
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Remove player");
		CloseActionListener.removeMinMaxClose(frame);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		icons = new ButtonGroup();
		panel = new JPanel();		
		panel.setLayout(new GridLayout(0,2));
		//create a button named after each player..
		PLAYER_LIST = ge.getAllPlayers();
		int i = 0;
		try {
			for (Player play : PLAYER_LIST) {
				createButtons(play, i++);
			}
		}catch (NullPointerException n) {
			panel.add(new JLabel(" No players at the moment."));
		}
		
		JButton button = new JButton("Close");
		button.addActionListener(new CloseActionListener(frame));
		frame.add("North", panel);
		frame.add("South", button);
	}
	//try to remove a player, error message if invalid
	public void removePlayer(Player player) {
		try {
			ge.removePlayer(player.getId());
			JOptionPane.showMessageDialog(null, String.format("%s has been removed from the game with a total of %s points.", player.getName()
					,player.getPoints()));
			panel.remove(buttons.get(player.getId()));
			buttons.remove(player.getId());
			panel.revalidate();
		} catch (NullPointerException r) {
			JOptionPane.showMessageDialog(null, r.getMessage());
		} catch (IllegalArgumentException a) {
			JOptionPane.showMessageDialog(null, a.getMessage());
		}
	}
	
	public AbstractButton createButtons(Player player, int i) {
		AbstractButton button = new JButton(player.getName());
		button.addActionListener(new RemovePlayerActionListener(this, player));
		icons.add(button);
		panel.add(button);
		buttons.put(player.getId(), button);
		return button;
	}
	
}
