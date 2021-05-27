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
import controller.AboutActionListener;
import controller.AddPlayerButtonListener;
import controller.ExitListener;
import controller.RemovePlayerButtonListener;
import controller.ToolbarButtonListener;


public class MenusBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private JMenuBar menu;
	private GameEngine ge;
	//placeholder listener
	Action buttonAction = new ToolbarButtonListener(ge);
	
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
		setKey('a', addBet);
		//addBet.addActionListener(buttonAction);
		addBet.addActionListener( e-> {
			new AddBetPanel(ge);
		});
		
		JMenuItem cancelBet = new JMenuItem("Cancel bet");
		setKey('c', cancelBet);
		cancelBet.addActionListener( e-> {
			new RemoveBetPanel(ge);
		});
		
		JMenuItem viewBets = new JMenuItem("View bets");
		setKey('v', viewBets);
		viewBets.addActionListener( e-> {
			new ViewBetsPanel(ge);
		});
		
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
		setKey('p', addPlayer);
		
		JMenuItem remPlayer = new JMenuItem("Remove Player");
		remPlayer.addActionListener(new RemovePlayerButtonListener(ge));
		setKey('r', remPlayer);
		
		JMenuItem addPoints = new JMenuItem("Add Points");
		setKey('o', addPoints);
		addPoints.addActionListener( e-> {
			new AddPointsPanel(ge);
		});
		
		JMenuItem end = new JMenuItem("Finish game");
		setKey('f', end);
		end.addActionListener(buttonAction);
		
		JMenuItem spin = new JMenuItem("Spin");
		setKey('s', spin);
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
		setKey('i', about);
		
		JMenuItem controls = new JMenuItem("Controls");
		setKey('h', controls);
		controls.addActionListener(buttonAction);
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		exit.addActionListener(new ExitListener());
		
		otherMenu.add(about);
		otherMenu.add(controls);
		otherMenu.add(exit);
		return otherMenu;
	}

	public void setKey(char key, JMenuItem item) {
		item.setAccelerator(KeyStroke.getKeyStroke(key));
	}

	public JMenuBar getMenu() {
		return this.menu;
	}

}

