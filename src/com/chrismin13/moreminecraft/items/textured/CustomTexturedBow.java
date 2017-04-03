package com.chrismin13.moreminecraft.items.textured;

import java.util.HashMap;
import java.util.Map;

import com.chrismin13.moreminecraft.durability.ItemDurability;
import com.chrismin13.moreminecraft.items.CustomBow;

import us.fihgu.toolbox.item.BowModelInjection;
import us.fihgu.toolbox.item.ModelInjection;
import us.fihgu.toolbox.item.ModelInjector;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.Predicate;

public class CustomTexturedBow extends CustomBow implements ModelInjector {

	public CustomTexturedBow(int amount, String customItemIdName, ItemDurability itemDurability,
			String defaultTexture) {
		super(amount, (short) 0, customItemIdName, itemDurability);
		this.defaultTexture = defaultTexture;
		this.addTexture(defaultTexture);
	}

	public CustomTexturedBow(int amount, String customItemIdName, String defaultTexture) {
		super(amount, (short) 0, customItemIdName);
		this.defaultTexture = defaultTexture;
		this.addTexture(defaultTexture);
	}

	private Map<String, Short> overrideModels = new HashMap<String, Short>();
	private String defaultTexture;

	@Override
	public Map<String, Short> getAllTextures() {
		return overrideModels;
	}

	@Override
	public Map<ModelInjection, Short> getOverrideEntries() {
		Map<ModelInjection, Short> map = new HashMap<ModelInjection, Short>();
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
