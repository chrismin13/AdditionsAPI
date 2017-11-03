package com.chrismin13.additionsapi.items.textured;

import java.util.HashMap;


import com.chrismin13.additionsapi.events.AdditionsAPIInitializationEvent;
import com.chrismin13.additionsapi.items.CustomTool;

import us.fihgu.toolbox.item.DamageableItem;
import us.fihgu.toolbox.item.ModelInjection;
import us.fihgu.toolbox.item.ModelInjector;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.Predicate;

/**
 * A {@link CustomTool} that contains at least one Texture since it implements
 * ModelInjector. A texture pack is required in order for the Item to function.
 * The Texture Pack will be provided during the
 * {@link AdditionsAPIInitializationEvent} using the
 * {@link AdditionsAPIInitializationEvent#addResourcePackFromPlugin(org.bukkit.plugin.java.JavaPlugin, String)}
 * or a similar method. The resource pack must have the following structure:
 * assets\plugin_name\textures\items\texture_name.png where plugin_name will be
 * the first part of the {@link CustomTool}'s Id Name (e.g. "vanilla_additions")
 * and the texture_name will be the name of the Texture that you specified when
 * creating the Item. It is necessary that the {@link CustomTexturedTool} is
 * registered during the {@link AdditionsAPIInitializationEvent} and that the
 * Texture Pack is received by the Player in order for the Texture to work.<br>
 * The main difference between a {@link CustomTexturedTool} and a
 * {@link CustomTexturedItem} is that the {@link CustomTexturedTool} will appear
 * as handheld, so that the stick part lines up with the hand of the player, as
 * seen with Swords, Axes, Pickaxes, Shovels and Hoes in-game.
 * 
 * @author chrismin13
 */
public class CustomTexturedTool extends CustomTool implements ModelInjector {

	private String defaultTexture;

	/**
	 * Creates a {@link CustomTexturedTool}
	 * 
	 * @param dItem
	 *            The DamageableItem that the {@link CustomTool} will be based
	 *            off. This will determine the Material of the
	 *            {@link CustomTool}.
	 * @param idName
	 *            the {@link CustomTool}'s ID Name. This MUST BE SIMILAR to
	 *            "vanilla_additions:emerald_sword" as the Texture will not be
	 *            able to be detected otherwise. It is also saved in the
	 *            ItemStack so you can easily detect which {@link CustomTool} it
	 *            is.
	 * @param defaultTexture
	 *            The name of the default texture. This will be the name of the
	 *            PNG file (without the extension .png) whose texture will be
	 *            used from the Resource Pack.
	 */
	public CustomTexturedTool(DamageableItem dItem, String idName, String defaultTexture) {
		super(dItem.getMaterial(), 1, (short) 0, idName);
		this.defaultTexture = defaultTexture;
		this.addTexture(defaultTexture);
	}

	private final HashMap<String, Short> overrideModels = new HashMap<String, Short>();

	@Override
	public HashMap<String, Short> getAllTextures() {
		return overrideModels;
	}

	@Override
	public HashMap<ModelInjection, Short> getOverrideEntries() {
		final HashMap<ModelInjection, Short> map = new HashMap<ModelInjection, Short>();
		String name = this.getIdName().split(":")[0];
		for (String texture : overrideModels.keySet()) {
			map.put(new ModelInjection(new Predicate(), name + ":item/" + texture,
					ItemModel.createSimpleItemModel("item/handheld", name + ":items/" + texture)),
					overrideModels.get(texture));
		}
		return map;
	}

	@Override
	public String getDefaultTexture() {
		return new String(defaultTexture);
	}
}
