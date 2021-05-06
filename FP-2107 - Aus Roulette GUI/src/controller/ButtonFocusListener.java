package controller;

import java.awt.event.*;

import javax.swing.AbstractButton;

public class ButtonFocusListener implements FocusListener {

	@Override
	public void focusGained(FocusEvent e) {
		System.out.println(getButtonName(e) + " gained focus.");
		checkTemporary(e);
	}

	@Override
	public void focusLost(FocusEvent e) {
		System.out.println(getButtonName(e) + " lost focus.");
		checkTemporary(e);
		
	}
	public String getButtonName(FocusEvent event) {
		return ((AbstractButton) event.getComponent()).getActionCommand();
	}
	public void checkTemporary(FocusEvent event) {
		System.out.println(event.isTemporary() ? ": temporary" : "");
	}
	
}
