package com.chrismin13.moreminecraft.api;

import org.bukkit.inventory.ItemStack;

import com.chrismin13.moreminecraft.files.DataFile;

public class CustomItemStack extends ItemStack {

	private CustomItem cItem;
	private String textureIdName;

	public CustomItemStack(CustomItem cItem, ItemStack stack) {
		super(stack);
		this.cItem = cItem;
		this.textureIdName = DataFile.getInstance().getTextureIdName(stack);
	}

	public CustomItem getCustomItem() {
		return cItem.clone();
	}
	
	public String getTextureIdName() {
		return new String(textureIdName);
	}
	
	public void setTexture(String textureIdName) {
		setType(DataFile.getInstance().getTextureMaterial(textureIdName));
		setDurability(DataFile.getInstance().getTextureDurability(textureIdName));
	}
	
}
