package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Model {

	private ToolbarItems selected;
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public Model() {
		super();
	}

	public ToolbarItems getSelectedColor() {
		return selected;
	}

	public void setSelectedColor(ToolbarItems selected) {
		ToolbarItems old = this.selected;
		this.selected = selected;

		this.pcs.firePropertyChange("selectedColor", old, selected);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.pcs.removePropertyChangeListener(listener);
	}

}
