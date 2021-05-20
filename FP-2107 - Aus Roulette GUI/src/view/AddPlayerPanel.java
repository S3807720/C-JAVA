package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import ausroulette.model.GameEngine;
import listener.AddPlayerListener;
import listener.CloseActionListener;

public class AddPlayerPanel extends JFrame {
	private JPanel panel, playerIcons, buttons;
	private JTextField idField, nameField, ipField;
	private JFrame frame;
	private ButtonGroup icons;
	private GameEngine ge;
	private String icon;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8152431565274127120L;

	public AddPlayerPanel(GameEngine ge) {
		this.ge = ge;
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Add player");
		CloseActionListener.removeMinMaxClose(frame);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		panel = new JPanel();
		buttons = new JPanel();	
		playerIcons = new JPanel();

		panel.setLayout(new GridLayout(6,2));
		playerIcons.setLayout(new GridLayout(2,2));
		createPlayerInputFields("Enter ID: ");
		idField = new JTextField(15);
		panel.add(idField);

		createPlayerInputFields("Enter Name: ");
		nameField = new JTextField(15);
		panel.add(nameField);

		createPlayerInputFields("Enter Initial Points: ");
		ipField = new JTextField(15);
		panel.add(ipField);
		
		icons = new ButtonGroup();
		int i = 0;
		createButtons(i++);
		createButtons(i++);
		createButtons(i++);
		createButtons(i);

		createIcons("images/jack.png");
		createIcons("images/aang.png");
		createIcons("images/square.png");
		createIcons("images/giraffe.png");
		
		buttons.setLayout(new FlowLayout());
		JButton button = new JButton("Add player");
		button.addActionListener(new AddPlayerListener(this));
		buttons.add(button);

		button = new JButton("Close");
		button.addActionListener(new CloseActionListener(frame));
		buttons.add(button);
		
		frame.setLayout(new BorderLayout());
		frame.add("North", panel);
		frame.add("Center", playerIcons);
		frame.add("South", buttons);
	}

	public JRadioButton createButtons(int i) {
		JRadioButton button = new JRadioButton();
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerSummaryPanel.setIconCounter(i);
			}
		});
		icons.add(button);
		playerIcons.add(button);
		return button;
	}
	
	public void createIcons(String direct) {
		JLabel icon = new JLabel(new ImageIcon(direct));
		playerIcons.add(icon);
	}
	
	//remove duplication..
	public void createPlayerInputFields(String descr) {
		JLabel label = new JLabel(descr);
		label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
		panel.add(label);
	}
	//create player, throw and catch errors w/ prompts
	public void createPlayer() {
		try {
			ge.addPlayer(idField.getText(), nameField.getText(), Integer.parseInt(ipField.getText()));
			//close parent window
			frame.dispose();
		} catch(NumberFormatException n) {
			JOptionPane.showMessageDialog(null, n.getMessage());
		} catch(IllegalArgumentException i) {
			JOptionPane.showMessageDialog(null, i.getMessage());
		} catch(NullPointerException o) {
			JOptionPane.showMessageDialog(null, o.getMessage());
		}
	}
	
}
