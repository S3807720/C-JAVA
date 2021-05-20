package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import ausroulette.model.GameEngine;
import ausroulette.model.GameEngineImpl;
import listener.AboutActionListener;
import listener.AddPlayerButtonListener;
import listener.ExitListener;
import listener.RemovePlayerButtonListener;
import listener.ToolbarButtonListener;


public class MenusBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private JMenuBar menu;
	private GameEngine ge;
	//placeholder listener
	Action buttonAction = new ToolbarButtonListener();
	
	public MenusBar(GameEngineImpl ge) {
		setLayout(new GridLayout());
		this.ge = ge;
		Border border = BorderFactory.createLineBorder(Color.DARK_GRAY);
		//create menu
		menu = new JMenuBar();
		menu.setBorder(border);
		menu.add(createBetsMenus());
		menu.add(createManageMenus());
		menu.add(createOtherMenus());
	}
	//bet related menu
	private JMenu createBetsMenus() {
		JMenu fileMenu = new JMenu("Bets");
		JMenuItem addBet = new JMenuItem("Add Bet");
		setKey('a', addBet, buttonAction);
		addBet.addActionListener(buttonAction);
		JMenuItem cancelBet = new JMenuItem("Cancel bet");
		setKey('c', cancelBet, buttonAction);
		cancelBet.addActionListener(buttonAction);
		JMenuItem viewBets = new JMenuItem("View bets");
		setKey('v', viewBets, buttonAction);
		viewBets.addActionListener(buttonAction);
		fileMenu.add(addBet);
		fileMenu.add(cancelBet);
		fileMenu.add(viewBets);
		return fileMenu;
	}
	
	//menus for managing game
	private JMenu createManageMenus() {
		JMenu manageMenu = new JMenu("Manage game");
		JMenuItem addPlayer = new JMenuItem("Add Player");
		addPlayer.addActionListener(new AddPlayerButtonListener(ge));
		setKey('p', addPlayer, addPlayer.getAction());
		JMenuItem remPlayer = new JMenuItem("Remove Player");
		remPlayer.addActionListener(new RemovePlayerButtonListener(ge));
		setKey('r', remPlayer, remPlayer.getAction());
		JMenuItem addPoints = new JMenuItem("Add Points");
		setKey('o', addPoints, buttonAction);
		addPoints.addActionListener( e-> {
			new AddPointsPanel(ge);
		});
		JMenuItem end = new JMenuItem("Finish game");
		setKey('f', end, buttonAction);
		end.addActionListener(buttonAction);
		JMenuItem spin = new JMenuItem("Spin");
		setKey('s', spin, buttonAction);
		spin.addActionListener(buttonAction);
		manageMenu.add(addPlayer);
		manageMenu.add(remPlayer);
		manageMenu.add(addPoints);
		manageMenu.add(spin);
		manageMenu.add(end);
		return manageMenu;
	}
	//about, controls, quit
	private JMenu createOtherMenus() {
		JMenu otherMenu = new JMenu("Other");
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new AboutActionListener());
		setKey('i', about, about.getAction());
		JMenuItem controls = new JMenuItem("Controls");
		setKey('h', controls, buttonAction);
		controls.addActionListener(buttonAction);
		JMenuItem exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		exit.addActionListener(new ExitListener());
		otherMenu.add(about);
		otherMenu.add(controls);
		otherMenu.add(exit);
		return otherMenu;
	}

	public void setKey(char key, JMenuItem item, Action buttonAction) {
		item.setAccelerator(KeyStroke.getKeyStroke(key));
	}

	public JMenuBar getMenu() {
		return this.menu;
	}

}

