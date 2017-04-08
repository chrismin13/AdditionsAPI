package com.chrismin13.moreminecraft.items;

import org.bukkit.Material;

import com.chrismin13.moreminecraft.files.DataFile;

/**
 * Used for Converting the Objects saved in the data.yml file. Meant mostly for
 * internal use in the API. In order to obtain one, use the methods is
 * {@link DataFile}
 * 
 * @author chrismin13
 *
 */
public class StorageCustomItem {

	private Material material;
	private short durability;
	private String idName;
	private String texture;

	public StorageCustomItem(Material material, short durability, String idName, String texture) {
		this.setMaterial(material);
		this.setDurability(durability);
		this.setIdName(idName);
		this.setTexture(texture);
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public short getDurability() {
		return durability;
	}

	public void setDurability(short durability) {
		this.durability = durability;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getTexture() {
		return texture;
	}

	public void setTexture(String texture) {
		this.texture = texture;
	}

	public CustomItemStack getCustomItemStack() {
		return new CustomItemStack(idName, durability, texture);
	}

	public CustomItem getCustomItem() {
		return getCustomItemStack().getCustomItem();
	}
}
