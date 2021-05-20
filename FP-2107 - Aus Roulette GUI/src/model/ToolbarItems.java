package model;

import javax.swing.*;

public enum ToolbarItems {
	ADD_BET, CANCEL_BET, SPIN;

	String name;
	ImageIcon icon;

	private ToolbarItems() {
		name = this.name().replace('_', ' ');
		icon = new ImageIcon(String.format("images/%s.png", this.name().toLowerCase()), this.name().toLowerCase());
	}


	public ImageIcon getIcon() {
		return this.icon;
	}

	public static ImageIcon getItem(String name) {
		ToolbarItems item = ToolbarItems.valueOf(name);
		ImageIcon icon = new ImageIcon(String.format("images/%s.png", item.name));
		return icon;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
