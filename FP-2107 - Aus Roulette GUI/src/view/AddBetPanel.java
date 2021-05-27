package view;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import ausroulette.model.GameEngine;
import ausroulette.model.Player;
import ausroulette.model.bet.BetType;
import ausroulette.model.wheel.PocketColor;
import ausroulette.model.wheel.Wheel;
import controller.BetTypeListener;
import controller.BoxActionListener;
import controller.CloseActionListener;
import controller.SliderChangeListener;

public class AddBetPanel extends JFrame {
	private JPanel panel;
	private JFrame frame;
	private ButtonGroup players;
	private GameEngine ge;
	private Map<String, AbstractButton> buttons = new HashMap<String, AbstractButton>();
	Collection<Player> PLAYER_LIST;
	private static final long serialVersionUID = 8152431565274127120L;
	JComboBox<Integer> dynamicBetNum = new JComboBox<Integer>();
	private BetType betType = BetType.BLACK;
	JOptionPane pane;

	public AddBetPanel(GameEngine ge) {
		//general frame look
		this.ge = ge;
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame = new JFrame("Add bet");
		CloseActionListener.removeMinMaxClose(frame);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		players = new ButtonGroup();
		panel = new JPanel();		
		panel.setLayout(new GridLayout(0,2));
		//create a button named after each player..
		PLAYER_LIST = ge.getAllPlayers();
		try {
			for (Player play : PLAYER_LIST) {
				createButtons(play);
			}
		}catch (NullPointerException n) {
			panel.add(new JLabel(" No players at the moment."));
		}
		JButton button = new JButton("Close");
		button.addActionListener(new CloseActionListener(frame));
		frame.add("North", panel);
		frame.add("South", button);
	}	
	
	public void setBetType(BetType type) {
		this.betType = type;
	}
	
	//show menu for bet choice
	public void displayBetMenu(Player player) {
		JOptionPane betChoice = new JOptionPane("", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
		JPanel panel = new JPanel();
		JButton[] buttons = new JButton[BetType.values().length];
		int i = 0;
		for (BetType type : BetType.values()) {
			buttons[i] = new JButton(type.toString());
			buttons[i].addActionListener(new BetTypeListener(player, type, this));
			buttons[i].addActionListener(new CloseActionListener(this));
			panel.add(buttons[i++]);
		}
		betChoice.add(panel);
		Dialog dialog = betChoice.createDialog(betChoice, "Place Bet");
	    dialog.setVisible(true);
	}
	
	public void numBetMenu(Player player) {
		//shared interface, then send to appropriate type..
		JTextField betEntry = new JTextField();
		JOptionPane pane = createMenu(betEntry, player);
		JComboBox<Integer> box = new JComboBox<Integer>();
		pane.setPreferredSize(new Dimension(175,320));
		panel.add(new JLabel("Enter number to bet: "));
		if (betType == BetType.SPLIT_4) {
			splitFourBet(pane, betEntry, box, player);
		} else if (betType == BetType.SPLIT_2) {
			splitTwoBet(pane, betEntry, box, player);
		} else {
			numberBets(pane, betEntry, box, player);
		}
	}
	
	public void numberBets(JOptionPane pane, JTextField betEntry, JComboBox<Integer> box, Player player) {
		for (int i = 0; i < Wheel.LARGEST_NUMBER; i++) {
			box.addItem(i);
		}
		panel.add(box);
		pane.add(panel);
		Dialog dialog = pane.createDialog(pane, "Place Bet");
	    dialog.setVisible(true);
	    if(!betEntry.getText().trim().equals("")) {
		    try {
		    	ge.placeNumberBet(player.getId(), Integer.parseInt(betEntry.getText()), (int) box.getSelectedItem());
		    	JOptionPane.getRootFrame().dispose();
		    } catch (IllegalStateException i) {
		    	JOptionPane.showMessageDialog(null, i.getMessage());
		    } catch(NumberFormatException n) {
				JOptionPane.showMessageDialog(null, "Bet amount must be numeric.");
			} catch(IllegalArgumentException a) {
		    	JOptionPane.showMessageDialog(null, a.getMessage());
		    } catch (NullPointerException n) {
		    	JOptionPane.showMessageDialog(null, n.getMessage());
		    } 
	    }
	}
	
	private void splitTwoBet(JOptionPane pane, JTextField betEntry, JComboBox<Integer> box, Player player) {
		//add nums
		for (int i = 1; i <= Wheel.LARGEST_NUMBER; i++) {
			box.addItem(i);
			box.addActionListener(new BoxActionListener(this, box));
		}
		panel.add(box);
		panel.add(dynamicBetNum);
		pane.add(panel);
		box.setSelectedIndex(0);
		Dialog dialog = pane.createDialog(pane, "Place Bet");
		dialog.setVisible(true);
	   //get numbers selected
		int[] betNums = { (int) box.getSelectedItem(), (int) dynamicBetNum.getSelectedItem() };
	    //place split bet and check for errors..
		if(!betEntry.getText().trim().equals("")) {
			try {
				placeBet(player, Integer.parseInt(betEntry.getText()), betNums);
			} catch(NumberFormatException n) {
				JOptionPane.showMessageDialog(null, "Bet amount must be numeric.");
			}
		}
	    
	}

	private void splitFourBet(JOptionPane pane, JTextField betEntry, JComboBox<Integer> box, Player player) {
		JComboBox<String> stringBox = new JComboBox<String>();
		int[][] nums = getFourSplit();
		//loop through bet choices and convert to displayable strings
		for (int[] num : nums) {
			stringBox.addItem(Arrays.toString(num));
		}
		panel.add(stringBox);
		pane.add(panel);
		stringBox.setSelectedIndex(0);
		Dialog dialog = pane.createDialog(pane, "Place Bet");
		dialog.setVisible(true);
		int[] betNums = removeSpecialChars(stringBox.getSelectedItem().toString());
		
		if(!betEntry.getText().trim().equals("")) {
			try {
				placeBet(player, Integer.parseInt(betEntry.getText()), betNums);
			} catch(NumberFormatException n) {
				JOptionPane.showMessageDialog(null, "Bet amount must be numeric.");
			}
			 
		}
	}
	
	//convert string back into int array..
	private int[] removeSpecialChars(String string) {
		//remove generic array to string symbols
		String str = string.replaceAll("[\\[\\]\\s+]", "");
		String[] strArr = str.split(",");
		int[] nums = new int[strArr.length];
		for(int i = 0; i < strArr.length; i++) {
			nums[i] = Integer.parseInt(strArr[i]);
		}
		return nums;
	}
	
	//remove duplication..
	private void placeBet(Player player, int num, int[] numbers) {
		try {
	    	ge.placeSplitBet(player.getId(), num, numbers);
	    	JOptionPane.getRootFrame().dispose();
	    } catch (IllegalStateException i) {
	    	JOptionPane.showMessageDialog(null, i.getMessage());
	    } catch(IllegalArgumentException a) {
	    	JOptionPane.showMessageDialog(null, a.getMessage());
	    } catch (NullPointerException n) {
	    	JOptionPane.showMessageDialog(null, n.getMessage());
	    }
	}
	
	private int[][] getFourSplit() {
		//hardcoded potential 4 split bets, would have preferred to create a loop but ran out of time
		int[][] numbers = new int[] [] {
	          { 1, 2, 4, 5}, { 2, 3, 5, 6},
	          { 4, 5, 7, 8}, { 5, 6, 8, 9},
	          { 7, 8, 10, 11}, { 8, 9, 11, 12},
	          { 10, 11, 13, 14}, { 11, 12, 14, 15}, 
	          { 13, 14, 16, 17 }, { 14, 15, 17, 18 }, 
	          { 16, 17, 19, 20 }, { 17, 18, 20, 21 },
	          { 19, 20, 22, 23 }, { 20, 21, 23, 24 }, 
	          { 22, 23, 25, 26 }, { 23, 24, 26, 27 },
	          { 25, 26, 28, 29 }, { 26, 27, 29, 30 }, 
	          { 28, 29, 31, 32 }, { 29, 30, 32, 33 }, 
	          { 31, 32, 34, 35 }, { 32, 33, 35, 36 }
	        };
		return numbers;
	}
	//dynamically fill the second box depending on first choice
	public void createTwoSplitBox(int num) {
		int numbers[] = getTwoSplit(num);
		dynamicBetNum.removeAllItems();
		for (int nums : numbers) {
			dynamicBetNum.addItem(nums);
		}
		dynamicBetNum.revalidate();
	}
	
	private int[] getTwoSplit(int num) {
		//generate number array for 1-3
		if (num < 4) {
			if (num % 3 == 2) {
				int[] numbers = { num+1, num-1, num+3 };
				return numbers;
			} else if (num % 3 == 1) {
				int[] numbers = { num+1, num+3 };
				return numbers;
			}
			else {
				int[] numbers = { num-1, num+3 };
				return numbers;
			}
			//generate number array for 34-36
		} else if (num > 33) {
			if (num % 3 == 2) {
				int[] numbers = { num+1, num-1, num-3 };
				return numbers;
			} else if (num % 3 == 1) {
				int[] numbers = { num+1, num-3 };
				return numbers;
			}
			else {
				int[] numbers = { num-3, num-1 };
				return numbers;
			}
		}
		//5,8...32
		else if (num % 3 == 2) {
			int[] numbers = { num+1, num-1, num+3, num-3 };
			return numbers;
		//4,7...31
		} else if (num % 3 == 1) {
			int[] numbers = { num+1, num+3, num-3 };
			return numbers;
		}
		//6,9...33
		else {
			int[] numbers = { num-3, num+3, num-1 };
			return numbers;
		}
	}
	
	public void colorBets(Player player) {
		JTextField betEntry = new JTextField("");
		JOptionPane pane = createMenu(betEntry, player);
		Dialog dialog = pane.createDialog(pane, "Place Bet");
	    dialog.setVisible(true);
	    PocketColor color = getColor();
	    if(!betEntry.getText().trim().equals("")) {
		    try {
		    	ge.placeColorBet(player.getId(),Integer.parseInt(betEntry.getText()), color);
		    	JOptionPane.getRootFrame().dispose();
		    } catch (IllegalStateException i) {
		    	JOptionPane.showMessageDialog(null, i.getMessage());
		    } catch(NumberFormatException n) {
				JOptionPane.showMessageDialog(null, "Bet amount must be numeric.");
			} catch(IllegalArgumentException a) {
		    	JOptionPane.showMessageDialog(null, a.getMessage());
		    } catch (NullPointerException n) {
		    	JOptionPane.showMessageDialog(null, n.getMessage());
		    } 
	    }
	}
	
	private PocketColor getColor() {
		PocketColor color;
		if (betType == BetType.BLACK) {
			color = PocketColor.BLACK;
		} else {
			color = PocketColor.RED;
		}
		return color;
	}

	private JOptionPane createMenu(JTextField numberEntry, Player player) {
		JOptionPane pointPane = new JOptionPane();
		panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		
		JLabel enterBet = new JLabel("Enter total points bet: ");
		numberEntry.setPreferredSize(new Dimension(50,50));
		JSlider slider = createSlider(numberEntry, player.getAvailablePoints());
		
		panel.add(slider);
		panel.add(enterBet);
		panel.add(numberEntry);
		
		pointPane.setMessage(panel);
		pointPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
	    return pointPane;
	}
	
	//optional slider
	public JSlider createSlider(JTextField numberEntry, int point) {
		int minPoint, maxPoint;
		minPoint = 0;
		maxPoint = point;
		JSlider points = new JSlider(JSlider.HORIZONTAL, minPoint, maxPoint, minPoint);
		points.setMajorTickSpacing(maxPoint);
		points.setMinorTickSpacing(maxPoint/5);
		points.setPaintTicks(true);
		points.setPaintLabels(true);
		points.addChangeListener(new SliderChangeListener(numberEntry));
		return points;
	}
	
	//create player choice screen..
	public AbstractButton createButtons(Player player) {
		AbstractButton button = new JButton(player.getName());
		button.addActionListener(e -> {
			displayBetMenu(player);
		});
		players.add(button);
		panel.add(button);
		buttons.put(player.getId(), button);
		return button;
	}
	
}