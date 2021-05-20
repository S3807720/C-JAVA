package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class AboutActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ShowMessageDialog();
	}

	public void ShowMessageDialog(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("About");
		CloseActionListener.removeMinMaxClose(frame);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setSize(470, 130);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();

		JLabel about = new JLabel("Luke Smith");
		panel.add(about);

		about = new JLabel("S3807720");
		panel.add(about);

		about = new JLabel("5/5/2021");
		panel.add(about);

		about = new JLabel("Image: http://nurseryrhymesforbabies.com/the-evolution-of-the-wheel/");
		panel.add(about);

		JButton button = new JButton("Close");
		button.addActionListener(new CloseActionListener(frame));
		panel.add(button);
		frame.add(panel);

	}
}
