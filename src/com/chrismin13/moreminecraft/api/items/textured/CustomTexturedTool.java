package com.chrismin13.moreminecraft.api.items.textured;

import java.util.HashMap;
import java.util.Map;

import com.chrismin13.moreminecraft.api.items.CustomTool;

import us.fihgu.toolbox.item.DamageableItem;
import us.fihgu.toolbox.item.ModelInjection;
import us.fihgu.toolbox.item.ModelInjector;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.Predicate;

public class CustomTexturedTool extends CustomTool implements ModelInjector{

	private String defaultTexture;
	
	public CustomTexturedTool(DamageableItem dItem, String customItemIdName, String defaultTexture) {
		super(dItem.getMaterial(), 1, (short) 0, customItemIdName);
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
		String name = this.getCustomItemIdName().split(":")[0];
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
