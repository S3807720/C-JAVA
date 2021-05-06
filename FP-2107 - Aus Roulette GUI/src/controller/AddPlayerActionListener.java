package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import ausroulette.model.Player;
import ausroulette.model.PlayerImpl;

public class AddPlayerActionListener implements ActionListener {
	JPanel panel;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		addPlayer();
	}
	
	public void addPlayer(){
		  JFrame.setDefaultLookAndFeelDecorated(true);
		  JFrame frame = new JFrame("Add player");
		  CloseActionListener.removeMinMaxClose(frame);
		  frame.setLocationRelativeTo(null);
		  frame.setResizable(false);
		  frame.setSize(300, 150);
		  frame.setVisible(true);
		  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		  
		  panel = new JPanel();
		  
		  createPlayerInputFields("Enter ID: ");
		  JTextField idField = new JTextField(15);
		  panel.add(idField);
		  
		  createPlayerInputFields("Enter Name: ");
		  JTextField nameField = new JTextField(15);
		  panel.add(nameField);
		  
		  createPlayerInputFields("Enter Initial Points: ");
		  JTextField ipField = new JTextField(15);
		  panel.add(ipField);
		  
		  JButton button = new JButton("Add player");
		  button.addActionListener(new CloseActionListener(frame));
		  button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idField.getText();
				String name = nameField.getText();
				String points = ipField.getText();
				if (idField.getText().isEmpty() || nameField.getText().isEmpty() || ipField.getText().isEmpty()) {
					throw new IllegalArgumentException("noooo");
				}
				try {
					Player player = new PlayerImpl(id, name, Integer.parseInt(points));
					System.out.println(String.format("Player %s has been added to the game!\n%s", name, player));
				} catch(NumberFormatException n) {
					System.out.println("Invalid number!!");
				} 

			}
			  
		  });
		  
		  
		  panel.add(button);
		  
		  button = new JButton("Close");
		  button.addActionListener(new CloseActionListener(frame));
		  panel.add(button);
		  
		  frame.add(panel);
		  
		  
	}
	
	//remove duplication..
	public void createPlayerInputFields(String descr) {
		JLabel label = new JLabel(descr);
		panel.add(label);
	}
	
}
