package com.chrismin13.moreminecraft.items.textured;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Color;

import com.chrismin13.moreminecraft.items.CustomLeatherArmor;
import com.chrismin13.moreminecraft.enums.ArmorType;
import us.fihgu.toolbox.item.DamageableItem;
import us.fihgu.toolbox.item.ModelInjection;
import us.fihgu.toolbox.item.ModelInjector;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.Predicate;

public class CustomTexturedLeatherArmor extends CustomLeatherArmor implements ModelInjector {

	private String defaultTexture;

	public CustomTexturedLeatherArmor(DamageableItem dItem, Color color, String customItemIdName, String defaultTexture) {
		super(ArmorType.getArmorType(dItem.getMaterial()), 1, (short) 0, customItemIdName, color);
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
