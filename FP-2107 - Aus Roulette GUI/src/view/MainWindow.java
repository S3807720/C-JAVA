package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import ausroulette.model.GameEngine;
import ausroulette.model.GameEngineImpl;
import ausroulette.model.Player;
import ausroulette.model.wheel.PocketColor;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private StatusBar status;
	private Toolbar toolbar;
	private WheelPanel wheelPanel;
	private MenusBar menu;
	private PlayerSummaryPanel playerInfo;
	private BettingTablePanel betTable;
	private GameEngine ge;

	public MainWindow(GameEngineImpl ge)  {
		super("Aus Roulette");
		this.ge = ge;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(900,600);
		setMinimumSize(new Dimension(600, 600));

		menu = new MenusBar(ge);
		status = new StatusBar(ge);
		toolbar = new Toolbar(ge);
		playerInfo = new PlayerSummaryPanel(ge);
		wheelPanel = new WheelPanel(ge);
		betTable = new BettingTablePanel(ge);
		testPlayers();
		this.setJMenuBar(menu.getMenu());
		add(toolbar, BorderLayout.NORTH);
		add(status, BorderLayout.SOUTH);
		add(playerInfo.getMain(), BorderLayout.WEST);
		add(wheelPanel, BorderLayout.CENTER);
		add(betTable, BorderLayout.EAST);
		add(createScrollFrame(), BorderLayout.WEST);
		setLocationRelativeTo(null);

	}
	public StatusBar getStatus() {
		return status;
	}
	public Toolbar getToolbar() {
		return toolbar;
	}
	public WheelPanel getWheelPanel() {
		return wheelPanel;
	}
	public MenusBar getMenu() {
		return menu;
	}
	public PlayerSummaryPanel getPlayerInfo() {
		return playerInfo;
	}
	public BettingTablePanel getBetTable() {
		return betTable;
	}
	public JScrollPane createScrollFrame() {
		JScrollPane scrollFrame=new JScrollPane(playerInfo.getMain(), 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,  
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollFrame.getVerticalScrollBar().setUnitIncrement(10);
		scrollFrame.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		return scrollFrame;
	}

	public void testPlayers() {
		Player player = ge.addPlayer("P1", "Mr Dollarbucks", 100);
		Player player2 = ge.addPlayer("P3", "Testy", 50);
		Player player3 = ge.addPlayer("P2", "Testy 2", 50);
		Player player4 = ge.addPlayer("P4", "Testy 3", 50);
		Player player5 = ge.addPlayer("P5", "Testy 4", 50);
		Player player6 = ge.addPlayer("P6", "Testy 5", 50);
		Player player7 = ge.addPlayer("P7", "Testy 6", 50);
		Player player8 = ge.addPlayer("P8", "Testy 7", 550);
		playerInfo.addPlayer(player);
		ge.placeColorBet("P1", 10, PocketColor.BLACK);
		ge.placeColorBet("P1", 2, PocketColor.BLACK);
		ge.placeColorBet("P1", 1, PocketColor.RED);
		ge.placeColorBet("P1", 10, PocketColor.GREEN);
		ge.placeNumberBet("P1", 1, 3);
		ge.placeNumberBet("P1", 1, 2);
		ge.placeNumberBet("P1", 1, 4);
		ge.placeNumberBet("P1", 1, 5);
		ge.placeNumberBet("P1", 1, 8);
		ge.placeNumberBet("P1", 1, 9);
		ge.placeNumberBet("P1", 1, 13);
		ge.placeNumberBet("P1", 1, 12);
		ge.placeSplitBet("P1", 1, new int[] { 1, 2, 4, 5});
		ge.placeSplitBet("P1", 1, new int[] { 12, 11});
		ge.placeNumberBet("P2", 22, 3);
		ge.placeNumberBet("P8", 232, 2);
		ge.placeNumberBet("P6", 22, 7);
		ge.placeNumberBet("P4", 22, 3);
		ge.placeSplitBet("P5", 50, new int[] { 1, 2, 4, 5});
		ge.placeSplitBet("P7", 50, new int[] { 12, 11});
		playerInfo.updateLabel(player);
		playerInfo.addPlayer(player2);
		playerInfo.addPlayer(player3);
		playerInfo.addPlayer(player4);
		playerInfo.addPlayer(player5);
		playerInfo.addPlayer(player6);
		playerInfo.addPlayer(player7);
		playerInfo.addPlayer(player8);
	}
}
