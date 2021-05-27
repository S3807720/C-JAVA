package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import ausroulette.model.Player;
import ausroulette.model.bet.Bet;
import controller.CloseActionListener;

public class ResultsScreen extends JFrame {

	private static final long serialVersionUID = 5262293402724165593L;

	public ResultsScreen(int spinNumber, Player player, Collection<Bet> bets, int amountWon, int amountLost) {
		super(String.format("Spin #%d %s Results", spinNumber, player.getName()));
		setLocationRelativeTo(null);
		setSize(350, 350);
		setVisible(true);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTop(spinNumber, player.getName(),amountWon, amountLost);		
		setBets(bets, player.getName());

		JButton button = new JButton("Close");
		button.setPreferredSize(new Dimension(50, 50));
		button.addActionListener(new CloseActionListener(this));
		add("South", button);
	}
	
	private void setBets (Collection<Bet> bets, String name) {
		JPanel panel = new JPanel();
		String betString = "<html>";
		if(bets.isEmpty()) {
			betString += name + " had no bets.";
		}
		for (Bet bet : bets) {
			
			
			betString += String.format("%s - %s<br>", !bet.isWin() ? "LOSS" : "WON", bet.toString() );
		}

		betString += "</html>";
		panel.add(new JLabel(betString), BorderLayout.WEST);

		JScrollPane scrollFrame=new JScrollPane(panel);
		scrollFrame.getVerticalScrollBar().setUnitIncrement(10);
		scrollFrame.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		add("Center", scrollFrame);
	}
	
	private void setTop(int spinN, String name, int won, int lost) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		panel.setMaximumSize(new Dimension(40,40));
		panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		panel.add(setValues(String.format("Won: %d", won)));
		panel.add(setValues(String.format("Lost: %d", lost)));
		panel.setPreferredSize(new Dimension(60,60));
		add("North", panel);
	}
	
	public JLabel setValues(String dets) {
		JLabel label = new JLabel(dets);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setPreferredSize(new Dimension(50, 40));	
		return label;
	}

}
