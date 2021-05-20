package listener;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JFrame;


public class CloseActionListener implements ActionListener {

	JFrame frame;

	//copy frame
	public CloseActionListener(JFrame frame) {
		this.frame = frame;
	}

	//dispose
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.dispose();
	}

	public static void removeMinMaxClose(Component comp)
	{
		if(comp instanceof AbstractButton)
		{
			comp.getParent().remove(comp);
		}
		if (comp instanceof Container)
		{
			Component[] comps = ((Container)comp).getComponents();
			for(int x = 0, y = comps.length; x < y; x++)
			{
				removeMinMaxClose(comps[x]);
			}
		}
	}
}
