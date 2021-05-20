package view;

import java.awt.Dialog;
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
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import listener.CloseActionListener;

public class AddBetPanel extends JFrame {
	private JPanel panel;
	private JFrame frame;
	private ButtonGroup icons;
	private GameEngine ge;
	private Map<String, AbstractButton> buttons = new HashMap<String, AbstractButton>();
	Collection<Player> PLAYER_LIST;
	private static final long serialVersionUID = 8152431565274127120L;

	public AddBetPanel(GameEngine ge) {
		//general frame look
		this.ge = ge;
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Add points");
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
	//try to add points, error message if invalid
	public void addPoints(Player player) {
		try {
			ge.removePlayer(player.getId());
			JOptionPane.showInputDialog(null, String.format("%s has been removed from the game with a total of %s points.", player.getName()
					,player.getPoints()));
			panel.remove(buttons.get(player.getId()));
			buttons.remove(player.getId());
			panel.revalidate();
		} catch (NullPointerException r) {
			JOptionPane.showMessageDialog(null, r.getMessage());
		} catch (IllegalArgumentException a) {
			JOptionPane.showMessageDialog(null, a.getMessage());
		}
	}
	
	public void displayPointMenu(Player player) {
		JOptionPane pointPane = new JOptionPane();
		JSlider slider = createSlider(pointPane, player.getAvailablePoints());
		pointPane.setMessage(new Object[] { "Points to add: ", slider });
		pointPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
		Dialog dialog = pointPane.createDialog(this, "Add Points");
	    dialog.setVisible(true);
	    ge.addPoints(player.getId(), Integer.parseInt(pointPane.getInputValue().toString()));
	}
	
	public JSlider createSlider(JOptionPane pointPane, int point) {
		int minPoint;
		minPoint = 0;
		JSlider points = new JSlider(JSlider.HORIZONTAL, minPoint, point, point/2);
		points.setMajorTickSpacing(point);
		points.setMinorTickSpacing(point/5);
		points.setPaintTicks(true);
		points.setPaintLabels(true);
		points.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider slider = (JSlider) e.getSource();
				if(!slider.getValueIsAdjusting()) {
					pointPane.setInputValue(slider.getValue());
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
