package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.Border;

import controller.AboutActionListener;
import controller.AddPlayerActionListener;


public class MenusBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private JMenuBar menu;
	
	public MenusBar() {
			setLayout(new GridLayout());
			
			Border border = BorderFactory.createLineBorder(Color.DARK_GRAY);
			menu = new JMenuBar();
			menu.setBorder(border);
			menu.add(createFileMenus());
			menu.add(createManageMenus());
			menu.add(createOtherMenus());
		}

		private JMenu createFileMenus() {
			JMenu fileMenu = new JMenu("Bets");
			JMenuItem addBet = new JMenuItem("Add bet");
			JMenuItem cancelBet = new JMenuItem("Cancel bet");
			JMenuItem viewBets = new JMenuItem("View bets");
			//exit.addActionListener(new CloseActionListener(this));
			fileMenu.add(addBet);
			fileMenu.add(cancelBet);
			fileMenu.add(viewBets);
			return fileMenu;
		}
		
		private JMenu createManageMenus() {
			JMenu manageMenu = new JMenu("Manage");
			JMenuItem addPlayer = new JMenuItem("Add Player");
			addPlayer.addActionListener(new AddPlayerActionListener());
			JMenuItem end = new JMenuItem("End game");
			JMenuItem restart = new JMenuItem("Restart game");
			manageMenu.add(addPlayer);
			manageMenu.add(end);
			manageMenu.add(restart);
			return manageMenu;
		}
		
		private JMenu createOtherMenus() {
			JMenu otherMenu = new JMenu("Other");
			JMenuItem about = new JMenuItem("About");
			about.addActionListener(new AboutActionListener());
			JMenuItem controls = new JMenuItem("Controls");
			JMenuItem exit = new JMenuItem("Exit");
			otherMenu.add(about);
			otherMenu.add(controls);
			otherMenu.add(exit);
			return otherMenu;
		}
		
		public JMenuBar getMenu() {
			return this.menu;
		}
		
	}

