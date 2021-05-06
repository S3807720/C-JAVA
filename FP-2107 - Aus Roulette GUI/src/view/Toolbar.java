package view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

import model.ToolbarItems;

public class Toolbar extends JToolBar {
	
	private static final long serialVersionUID = 1L;
	//private JPanel bg;

	public Toolbar() {
		setSize(200,200);
		setLayout(new GridLayout());
		//Border border = BorderFactory.createLineBorder(Color.BLACK);
		//bg = new JPanel();

		for (ToolbarItems item : ToolbarItems.values()) {
			AbstractButton button = new JButton(item.toString());
			//button.setBounds(10,10,15,10);
			button.setPreferredSize(new Dimension(60, 60));
			add(button);
			//add(button);
			//button.addActionListener(new ToolbarButtonListener(item, circleModel));

		}
		//add(bg);
	}
}
