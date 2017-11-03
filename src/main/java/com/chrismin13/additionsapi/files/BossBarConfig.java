package com.chrismin13.additionsapi.files;

import com.chrismin13.additionsapi.items.CustomItem;

/**
 * Holds the values of the config.yml's sub-section specifying the BossBar(s)'s
 * behavior in regards to if and how it will display durability values.
 * 
 * @author chris
 *
 */
public class BossBarConfig {

	/**
	 * Create an instance of this Class.
	 * 
	 * @param show
	 *            Whether the BossBar(s) will be displayed or not.
	 * @param vanillaItems
	 *            Whether durability for the Damageable Items already in the
	 *            game will be displayed in the BossBar(s).
	 * @param customItems
	 *            Whether durability for the {@link CustomItem}s will be
	 *            displayed in the BossBar(s).
	 */
	public BossBarConfig(boolean show, boolean vanillaItems, boolean customItems) {
		this.show = show;
		this.vanillaItems = vanillaItems;
		this.customItems = customItems;
	}

	private boolean show;
	private boolean vanillaItems;
	private boolean customItems;

	/**
	 * @return Whether the BossBar(s) will be displayed or not.
	 */
	public boolean show() {
		return show;
	}

	/**
	 * @return Whether durability for the Damageable Items already in the game
	 *         will be displayed in the BossBar(s).
	 */
	public boolean showVanillaItems() {
		return vanillaItems;
	}

	/**
	 * @return Whether durability for the {@link CustomItem}s will be displayed
	 *         in the BossBar(s).
	 */
	public boolean showCustomItems() {
		return customItems;
	}
}