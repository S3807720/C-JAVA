package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import ausroulette.model.GameEngineImpl;
import ausroulette.model.Player;
import controller.BetInfoMouseListener;

public class PlayerSummaryPanel extends JPanel {
	private final String[] ICON_IMAGES = { "images/jack.png", "images/aang.png", "images/square.png", "images/giraffe.png" };
	private static int counter = 0;
	private static final long serialVersionUID = -1725754453212405795L;
	private JSplitPane main;
	private JPanel titles = new JPanel();
	private JPanel data = new JPanel();

	private Map<String, JLabel> playerNames = new HashMap<String, JLabel>();
	private Map<String, JLabel> playerAvails = new HashMap<String, JLabel>();
	private Map<String, JLabel> playerTotalPoints = new HashMap<String, JLabel>();
	private Map<String, JLabel> playerBets = new HashMap<String, JLabel>();
	private Map<String, JLabel> playerIcons = new HashMap<String, JLabel>();

	public PlayerSummaryPanel(GameEngineImpl ge) {
		titles.setLayout(new GridLayout(2, 5));
		data.setLayout(new GridLayout(0,5));
		main = new JSplitPane(JSplitPane.VERTICAL_SPLIT, titles, data);
		main.setDividerLocation(0.1);
		main.setPreferredSize(new Dimension(250, 600));
		main.setLayout(new FlowLayout());
		createHeadingPanel();
	}

	public static void setIconCounter(int count) {
		counter = count;
	}
	
	public void createHeadingPanel() {
		titles.add(setValues(""));
		titles.add(setValues("Player"));
		titles.add(setValues("Available"));
		titles.add(setValues("Total"));
		titles.add(setValues("Total"));
		titles.add(setValues(""));
		titles.add(setValues("Name"));
		titles.add(setValues("Points"));
		titles.add(setValues("Points"));
		titles.add(setValues("Bets"));
		titles.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
	}
	
	//bets are currently only viewable by clicking playerBets, will be a window in the menu later, probably
	public void addPlayer(Player player) {
		ImageIcon image = new ImageIcon(ICON_IMAGES[counter]);
		JLabel playerIcon = new JLabel(image);
		playerIcon.setPreferredSize(new Dimension(50, 40));	
		data.add(playerIcon);
		JLabel playerName = setValues(player.getName(), player);
		playerName.setToolTipText(player.getName());
		JLabel playerAvail = setValues(String.valueOf(player.getAvailablePoints()), player);
		JLabel playerTotal = setValues(String.valueOf(player.getPoints()), player);
		JLabel playerBet = setValues(String.valueOf(player.getCurrentBetTotal()),player);
		playerIcons.put(player.getId(), playerIcon);
		playerNames.put(player.getId(), playerName);
		playerAvails.put(player.getId(), playerAvail);
		playerTotalPoints.put(player.getId(), playerTotal);
		playerBets.put(player.getId(), playerBet);
		data.revalidate();
	}
	
	//bets are currently only viewable by clicking playerBets, will be a window in the menu later, probably
	public void removePlayer(Player player) {
		data.remove(playerIcons.get(player.getId()));
		playerIcons.remove(player.getId());
		data.remove(playerNames.get(player.getId()));
		playerNames.remove(player.getId());
		data.remove(playerAvails.get(player.getId()));
		playerAvails.remove(player.getId());
		data.remove(playerTotalPoints.get(player.getId()));
		playerTotalPoints.remove(player.getId());
		data.remove(playerBets.get(player.getId()));
		playerBets.remove(player.getId());
		data.revalidate();
	}
	
	//update label
	public void updateLabel(Player player) {
		
		playerNames.get(player.getId()).setText(player.getName());
		
		playerAvails.get(player.getId()).setText(String.valueOf(player.getAvailablePoints()));
		playerAvails.get(player.getId()).setToolTipText(player.getBets().toString());
		
		playerTotalPoints.get(player.getId()).setText(String.valueOf(player.getPoints()));
		playerTotalPoints.get(player.getId()).setToolTipText(player.getBets().toString());
		
		playerBets.get(player.getId()).setText(String.valueOf(player.getCurrentBetTotal()));
		playerBets.get(player.getId()).setToolTipText(player.getBets().toString());
		data.revalidate();
	}
	
	//helper method to set label specifics
	public JLabel setValues(String dets, Player player) {
		JLabel label = new JLabel(dets);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.addMouseListener(new BetInfoMouseListener(player));
		label.setToolTipText(player.getBets().toString());
		label.setPreferredSize(new Dimension(50, 40));	
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		data.add(label);
		return label;
	}
	
	//helper method to set label specifics
	public JLabel setValues(String dets) {
		JLabel label = new JLabel(dets);
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}

	public JSplitPane getMain() {
		return this.main;
	}

}
