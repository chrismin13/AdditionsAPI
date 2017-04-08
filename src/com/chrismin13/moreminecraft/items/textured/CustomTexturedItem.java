package com.chrismin13.moreminecraft.items.textured;

import java.util.HashMap;
import java.util.Map;

import com.chrismin13.moreminecraft.events.MoreMinecraftAPIInitializationEvent;
import com.chrismin13.moreminecraft.items.CustomItem;
import us.fihgu.toolbox.item.DamageableItem;
import us.fihgu.toolbox.item.ModelInjection;
import us.fihgu.toolbox.item.ModelInjector;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.Predicate;

/**
 * A {@link CustomItem} that contains at least one Texture since it implements
 * ModelInjector. A texture pack is required in order for the Item to function.
 * The Texture Pack will be provided during the
 * {@link MoreMinecraftAPIInitializationEvent} using the
 * {@link MoreMinecraftAPIInitializationEvent#addResourcePackFromPlugin(org.bukkit.plugin.java.JavaPlugin, String)}
 * or a similar method. The resource pack must have the following structure:
 * assets\plugin_name\textures\items\texture_name.png where plugin_name will be
 * the first part of the {@link CustomItem}'s Id Name (e.g. "vanilla_additions")
 * and the texture_name will be the name of the Texture that you specified when
 * creating the Item. It is necessary that the {@link CustomTexturedItem} is
 * registered during the {@link MoreMinecraftAPIInitializationEvent} and that
 * the Texture Pack is received by the Player in order for the Texture to work.
 * 
 * @author chrismin13
 *
 */
public class CustomTexturedItem extends CustomItem implements ModelInjector {

	private String defaultTexture;

	/**
	 * Creates a {@link CustomTexturedItem}
	 * 
	 * @param dItem
	 *            The DamageableItem that the CustomItem will be based off. This
	 *            will determine the Material of the {@link CustomItem}.
	 * @param idName
	 *            the {@link CustomItem}'s ID Name. This MUST BE SIMILAR to
	 *            "vanilla_additions:emerald_sword" as the Texture will not be
	 *            able to be detected otherwise. It is also saved in the
	 *            ItemStack so you can easily detect which {@link CustomItem} it
	 *            is.
	 * @param defaultTexture
	 *            The name of the default texture. This will be the name of the
	 *            PNG file (without the extension .png) whose texture will be
	 *            used from the Resource Pack.
	 */
	public CustomTexturedItem(DamageableItem dItem, int amount, String idName, String defaultTexture) {
		super(dItem.getMaterial(), amount, (short) 0, idName);
		this.defaultTexture = defaultTexture;
		this.addTexture(defaultTexture);
	}

	private Map<String, Short> overrideModels = new HashMap<String, Short>();

	@Override
	public Map<String, Short> getAllTextures() {
		return overrideModels;
	}

	@Override
	public Map<ModelInjection, Short> getOverrideEntries() {
		Map<ModelInjection, Short> map = new HashMap<ModelInjection, Short>();
		String name = this.getIdName().split(":")[0];
		for (String texture : overrideModels.keySet()) {
			map.put(new ModelInjection(new Predicate(), name + ":item/" + texture,
					ItemModel.createSimpleItemModel("item/generated", name + ":items/" + texture)),
					overrideModels.get(texture));
		}
		return map;
	}

	@Override
	public String getDefaultTexture() {
		return new String(defaultTexture);
	}
}