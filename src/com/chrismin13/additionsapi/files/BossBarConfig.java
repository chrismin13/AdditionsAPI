package com.chrismin13.additionsapi.files;

public class BossBarConfig {

	public BossBarConfig(boolean show, boolean vanillaItems, boolean customItems) {
		this.show = show;
		this.vanillaItems = vanillaItems;
		this.customItems = customItems;
	}

	private boolean show;
	private boolean vanillaItems;
	private boolean customItems;

	/**
	 * @return the show
	 */
	public boolean show() {
		return show;
	}

	/**
	 * @return the vanillaItems
	 */
	public boolean showVanillaItems() {
		return vanillaItems;
	}

	/**
	 * @return the customItems
	 */
	public boolean showCustomItems() {
		return customItems;
	}

}
