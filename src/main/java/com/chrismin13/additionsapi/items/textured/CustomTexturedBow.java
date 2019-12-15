package com.chrismin13.additionsapi.items.textured;

import java.util.HashMap;

import com.chrismin13.additionsapi.durability.ItemDurability;
import com.chrismin13.additionsapi.events.AdditionsAPIInitializationEvent;
import com.chrismin13.additionsapi.items.CustomBow;
import com.chrismin13.additionsapi.items.CustomItem;

import us.fihgu.toolbox.item.BowModelInjection;
import us.fihgu.toolbox.item.ModelInjection;
import us.fihgu.toolbox.item.ModelInjector;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.Predicate;

/**
 * A {@link CustomItem} that contains at least one Texture since it implements
 * ModelInjector. A texture pack is required in order for the Item to function.
 * The Texture Pack will be provided during the
 * {@link AdditionsAPIInitializationEvent} using the
 * {@link AdditionsAPIInitializationEvent#addResourcePackFromPlugin(org.bukkit.plugin.java.JavaPlugin, String)}
 * or a similar method. The resource pack must have the following structure:
 * assets\plugin_name\textures\items\texture_name_standby.png where plugin_name
 * will be the first part of the {@link CustomItem}'s Id Name (e.g.
 * "vanilla_additions") and the texture_name will be the name of the Texture
 * that you specified when creating the Item. You must also include
 * texture_name_pulling0.png, texture_name_pulling1.png and
 * texture_name_pulling2.png for the rest of the Bow's states. It is necessary
 * that the {@link CustomTexturedBow} is registered during the
 * {@link AdditionsAPIInitializationEvent} and that the Texture Pack is
 * received by the Player in order for the Texture to work.
 * 
 * @author chrismin13
 *
 */
public class CustomTexturedBow extends CustomBow implements ModelInjector {

	/**
	 * Creates a {@link CustomTexturedBow}
	 * 
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
	public CustomTexturedBow(int amount, String idName, ItemDurability itemDurability, String defaultTexture) {
		super(amount, (short) 0, idName, itemDurability);
		this.defaultTexture = defaultTexture;
		this.addTexture(defaultTexture);
	}

	/**
	 * Creates a {@link CustomTexturedBow}.
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
	public CustomTexturedBow(int amount, String idName, String defaultTexture) {
		super(amount, (short) 0, idName);
		this.defaultTexture = defaultTexture;
		this.addTexture(defaultTexture);
	}

	private final HashMap<String, Short> overrideModels = new HashMap<String, Short>();
	private String defaultTexture;

	@Override
	public HashMap<String, Short> getAllTextures() {
		return overrideModels;
	}

	@Override
	public HashMap<ModelInjection, Short> getOverrideEntries() {
		final HashMap<ModelInjection, Short> map = new HashMap<ModelInjection, Short>();
		String name = this.getIdName().split(":")[0];
		Predicate standby = new Predicate();
		standby.setPulling(0);
		Predicate pulling0 = new Predicate();
		pulling0.setPulling(1);
		Predicate pulling1 = new Predicate();
		pulling1.setPulling(1);
		pulling1.setPull(0.65);
		Predicate pulling2 = new Predicate();
		pulling2.setPulling(1);
		pulling2.setPull(0.9);
		for (String texture : overrideModels.keySet()) {
			texture = texture.toLowerCase();
			map.put(new BowModelInjection(standby, name + ":item/" + texture,
					ItemModel.createSimpleItemModel("item/bow", name + ":items/" + texture + "_standby"), true),
					overrideModels.get(texture));
			map.put(new BowModelInjection(pulling0, name + ":item/" + texture + "_pulling_0",
					ItemModel.createSimpleItemModel("item/bow", name + ":items/" + texture + "_pulling_0"), false),
					overrideModels.get(texture));
			map.put(new BowModelInjection(pulling1, name + ":item/" + texture + "_pulling_1",
					ItemModel.createSimpleItemModel("item/bow", name + ":items/" + texture + "_pulling_1"), false),
					overrideModels.get(texture));
			map.put(new BowModelInjection(pulling2, name + ":item/" + texture + "_pulling_2",
					ItemModel.createSimpleItemModel("item/bow", name + ":items/" + texture + "_pulling_2"), false),
					overrideModels.get(texture));
		}
		return map;
	}

	@Override
	public String getDefaultTexture() {
		return new String(defaultTexture);
	}

}
