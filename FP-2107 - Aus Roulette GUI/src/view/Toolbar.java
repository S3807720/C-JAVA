package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import ausroulette.model.GameEngineImpl;
import listener.ToolbarButtonListener;
import model.ToolbarItems;

public class Toolbar extends JToolBar {

	private static final long serialVersionUID = 1L;

	public Toolbar(GameEngineImpl ge) {
		setLayout(new GridLayout());
		Border border = BorderFactory.createLineBorder(Color.DARK_GRAY);
		for (ToolbarItems item : ToolbarItems.values()) {
			char key = item.toString().toLowerCase().charAt(0);

			AbstractButton button = new JButton(item.toString());
			button.setPreferredSize(new Dimension(60, 60));
			button.setBorder(border);

			button.setMnemonic(key);

			Action buttonAction = new ToolbarButtonListener();

			button.addActionListener(buttonAction);
			add(button);


		}

	}
}
