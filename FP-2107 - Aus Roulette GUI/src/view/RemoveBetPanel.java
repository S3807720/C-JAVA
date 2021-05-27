package view;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.model.bet.Bet;
import controller.CloseActionListener;
import controller.RemoveBetListener;
import controller.RemovePlayerMenuListener;

public class RemoveBetPanel extends JFrame {

	private static final long serialVersionUID = -6600815169724320741L;
	private JPanel panel;
	private JFrame frame;
	private ButtonGroup players;
	private GameEngine ge;
	private JComboBox<String> box;
	private Map<String, AbstractButton> buttons = new HashMap<String, AbstractButton>();
	private Collection<Player> PLAYER_LIST;
	private Bet bet = null;

	public RemoveBetPanel(GameEngine ge) {
		//general frame look
		this.ge = ge;
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Cancel bet");
		CloseActionListener.removeMinMaxClose(frame);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		players = new ButtonGroup();
		panel = new JPanel();		
		panel.setLayout(new GridLayout(0,2));
		//create a button named after each player..
		PLAYER_LIST = ge.getAllPlayers();
		int i = 0;
		try {
			for (Player play : PLAYER_LIST) {
				if (!play.getBets().isEmpty()) {
					createButtons(play, i++);
				}
				
			}
		}catch (NullPointerException n) {
			panel.add(new JLabel(" No players at the moment."));
		}
		JButton button = new JButton("Close");
		button.addActionListener(new CloseActionListener(frame));
		frame.add("North", panel);
		frame.add("South", button);
	}	

	//show menu for bet choice
	public void betMenu(Player player) {
		//shared interface, then send to appropriate type..
		JOptionPane pane = createMenu(player);
		box = new JComboBox<String>();
		pane.setPreferredSize(new Dimension(175, 120));
		panel.add(new JLabel("Choose bet to remove: "));
		box.addItem("");
		for (Bet bet : player.getBets()) {
			box.addItem(String.format("%s.", bet.toString()));
			box.addActionListener(new RemoveBetListener(this, player));
		}
		panel.add(box);
		pane.add(panel);	
		Dialog dialog = pane.createDialog(pane, "Cancel Bet");
		dialog.setVisible(true);
	    if(bet != null) {
			try {			
				ge.cancelBet(bet);
				JOptionPane.getRootFrame().dispose();
			} catch (IllegalStateException i) {
				JOptionPane.showMessageDialog(null, i.getMessage());
			} catch(IllegalArgumentException a) {
				JOptionPane.showMessageDialog(null, a.getMessage());
			} catch (NullPointerException n) {
				JOptionPane.showMessageDialog(null, n.getMessage());
			} 
	    }
	
	}

	public void setBet(Player player) {
		String tempBet = box.getSelectedItem().toString();		
		for (Bet bet : player.getBets()) {
			if (String.format("%s.", bet.toString()).equals(tempBet)) {
				this.bet = bet;
			}
		}
	}
	
	private JOptionPane createMenu(Player player) {
		JOptionPane pointPane = new JOptionPane();
		panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));

		pointPane.setMessage(panel);
		pointPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
		return pointPane;
	}

	//create player choice screen..
	public AbstractButton createButtons(Player player, int i) {
		AbstractButton button = new JButton(player.getName());
		button.addActionListener(new RemovePlayerMenuListener(player, this));
		players.add(button);
		panel.add(button);
		buttons.put(player.getId(), button);
		return button;
	}

}