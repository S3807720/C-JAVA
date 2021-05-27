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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import controller.CloseActionListener;

public class AddPointsPanel extends JFrame {
	private JPanel panel;
	private JFrame frame;
	private ButtonGroup icons;
	private GameEngine ge;
	private Map<String, AbstractButton> buttons = new HashMap<String, AbstractButton>();
	Collection<Player> PLAYER_LIST;
	private static final long serialVersionUID = 8152431565274127120L;

	public AddPointsPanel(GameEngine ge) {
		//general frame look
		this.ge = ge;
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Add Points to Player");
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
	//show mini menu for points
	public void displayPointMenu(Player player) {
		JOptionPane pointPane = new JOptionPane();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		JTextArea numberEntry = new JTextArea();
		numberEntry.setPreferredSize(new Dimension(50,50));
		JSlider slider = createSlider(numberEntry);
		panel.add(slider);
		panel.add(numberEntry);
		pointPane.setMessage(panel);
		Dialog dialog = pointPane.createDialog(this, "Add Points");
	    dialog.setVisible(true);
	    //if empty, close prompt
	    if(!numberEntry.getText().trim().equals("")) {
		    try {
		    	if (Long.parseLong(numberEntry.getText()) > Integer.MAX_VALUE) {
		    		throw new IllegalArgumentException("That number is too high!");
		    	}
		    	if (Integer.parseInt(numberEntry.getText()) < 0 ) { 
		    		throw new IllegalArgumentException("Points must be over 0!");
		    	}
		    	if (JOptionPane.showConfirmDialog(null, String.format("Are you sure you want to add %d points to %s?", 
			    		Integer.parseInt(numberEntry.getText()), player.getName()), 
			    		"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					ge.addPoints(player.getId(), Integer.parseInt(numberEntry.getText()));			
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Must enter a valid whole number!");
			} catch (IllegalArgumentException i) {
				JOptionPane.showMessageDialog(null, i.getMessage());
			}
	    }
	    
	    
	}
	//optional slider
	public JSlider createSlider(JTextArea numberEntry) {
		int minPoint, maxPoint;
		minPoint = 0;
		maxPoint = 500;
		JSlider points = new JSlider(JSlider.HORIZONTAL, minPoint, maxPoint, minPoint);
		points.setMajorTickSpacing(maxPoint);
		points.setMinorTickSpacing(maxPoint/5);
		points.setPaintTicks(true);
		points.setPaintLabels(true);
		points.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				if(!slider.getValueIsAdjusting()) {
					numberEntry.setText(String.valueOf(slider.getValue()));
				}
			}
		});
		return points;
	}
	
	public AbstractButton createButtons(Player player, int i) {
		AbstractButton button = new JButton(player.getName());
		button.addActionListener(e -> {
			displayPointMenu(player);
		});
		icons.add(button);
		panel.add(button);
		buttons.put(player.getId(), button);
		return button;
	}
	
}
