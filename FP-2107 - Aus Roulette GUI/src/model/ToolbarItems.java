package model;

public enum ToolbarItems {
	ADD_BET, CANCEL_BET, SPIN;

	String name;

	private ToolbarItems() {
		name = this.name().replace('_', ' ');
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
