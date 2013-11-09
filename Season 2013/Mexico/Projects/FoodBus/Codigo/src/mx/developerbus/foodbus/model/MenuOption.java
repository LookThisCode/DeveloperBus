package mx.developerbus.foodbus.model;

import mx.developerbus.foodbus.enm.MenuOptionType;

public class MenuOption {

	private MenuOptionType type;
	private int resourceId;
	private String title;
	private boolean selected;
	

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public MenuOptionType getType() {
		return type;
	}

	public void setType(MenuOptionType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;

	}

	public boolean isSelected(boolean b) {
		return selected;

	}
	
}
