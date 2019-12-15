package com.chrismin13.additionsapi.items.textured;

import java.util.HashMap;

import org.bukkit.Color;

import com.chrismin13.additionsapi.enums.ArmorType;
import com.chrismin13.additionsapi.events.AdditionsAPIInitializationEvent;
import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.CustomLeatherArmor;

import us.fihgu.toolbox.item.DamageableItem;
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
 * assets\plugin_name\textures\items\texture_name.png where plugin_name will be
 * the first part of the {@link CustomItem}'s Id Name (e.g. "vanilla_additions")
 * and the texture_name will be the name of the Texture that you specified when
 * creating the Item. It is necessary that the {@link CustomTexturedItem} is
 * registered during the {@link AdditionsAPIInitializationEvent} and that
 * the Texture Pack is received by the Player in order for the Texture to work.
 * <br>
 * Unfortunately, due to Minecraft limitations, full Custom Textured Armor with
 * a Custom Texture for the skin that you see in F5 Mode is impossible. Only the
 * item that is held in the hand can have its texture changed. Using this
 * however, you can achieve a similar look as the plugin provides by default a
 * Leather Armor skin that looks more like normal armor (similar to Gold and
 * Iron) to make it harder to see that it's a Leather Armor. The item, however,
 * will still contain text saying "Dyed" breaking <i>slightly</i> the illusion.
 * 
 * @author chrismin13
 *
 */
public class CustomTexturedLeatherArmor extends CustomLeatherArmor implements ModelInjector {

	private String defaultTexture;

	/**
	 * Creates a {@link CustomTexturedLeatherArmor}
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
	public CustomTexturedLeatherArmor(DamageableItem dItem, Color color, String idName, String defaultTexture) {
		super(ArmorType.getArmorType(dItem.getMaterial()), 1, (short) 0, idName, color);
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
			texture = texture.toLowerCase();
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
