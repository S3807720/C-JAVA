package controller;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderChangeListener implements ChangeListener {

	private JTextField numberEntry;

	public SliderChangeListener(JTextField numberEntry) {
		this.numberEntry = numberEntry;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();
		if(!slider.getValueIsAdjusting()) {
			numberEntry.setText(String.valueOf(slider.getValue()));
		}
	}

}
