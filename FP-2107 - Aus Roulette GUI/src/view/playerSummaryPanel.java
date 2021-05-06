package view;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import ausroulette.model.Player;

public class playerSummaryPanel extends JPanel implements PropertyChangeListener {

	private static final long serialVersionUID = -1725754453212405795L;
	private JPanel main = new JPanel();
	Border border = BorderFactory.createLineBorder(Color.GRAY);
	private Map<String, JLabel> playerBets = new HashMap<String, JLabel>();
	
	public playerSummaryPanel() {
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		
	}

	public void addPlayer(Player player) {
		JLabel playerName = new JLabel(player.getName(), SwingConstants.RIGHT);
		JLabel playerAvailPoints = new JLabel("Avail. Points: " + player.getAvailablePoints(), SwingConstants.RIGHT);
		JLabel playerTotalPoints = new JLabel("Total Points: " + player.getPoints(), SwingConstants.RIGHT);
		JLabel playerBets = new JLabel("Total bet: " + player.getCurrentBetTotal(), SwingConstants.RIGHT);
		this.playerBets.put(player.getId(), playerBets);
		playerName.setToolTipText("Player " + player.getName());
		playerAvailPoints.setToolTipText(player.getName() +" has " + player.getAvailablePoints() + " points available.");
		playerTotalPoints.setToolTipText(player.getName() + " has a total of " +player.getPoints() + " points.");
		playerBets.setToolTipText(player.getBets().toString());
		JLabel blank = new JLabel(" ", SwingConstants.RIGHT);
		setValues(playerName);
		setValues(playerAvailPoints);
		setValues(playerTotalPoints);
		setValues(playerBets);
		main.add(blank);
	}
	
	public void editLabel(Player player) {
		this.playerBets.get("P1").setText("Total bet: " + player.getCurrentBetTotal());
		this.playerBets.get("P1").setToolTipText(player.getBets().toString());
	}
	
	public void setValues(JLabel label) {
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(border);
		label.setMaximumSize(new Dimension(125, 20));
		main.add(label);
	}
	
	public void updateText(String thing) {
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	
	}
	
	public JPanel getMain() {
		return this.main;
	}

}
