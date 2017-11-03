package com.chrismin13.additionsapi.items;

import org.bukkit.Material;

import com.chrismin13.additionsapi.files.DataFile;
import com.chrismin13.additionsapi.items.textured.CustomTexturedItem;

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

	/**
	 * Create a new {@link StorageCustomItem}. This is just a Java Object and
	 * won't be saved to the Data.yml file. For that, use the methods at
	 * {@link DataFile}.
	 * 
	 * @param material
	 *            The Material of the {@link StorageCustomItem}.
	 * @param durability
	 *            The Durability Value of the {@link StorageCustomItem}
	 * @param idName
	 *            The ID Name of the {@link StorageCustomItem}
	 * @param texture
	 *            The Texture Name of the {@link StorageCustomItem}
	 */
	public StorageCustomItem(Material material, short durability, String idName, String texture) {
		this.setMaterial(material);
		this.setDurability(durability);
		this.setIdName(idName);
		this.setTexture(texture);
	}

	/**
	 * Create a new {@link StorageCustomItem}. The values for the ID Name and
	 * Texture Name will be generated form the values of the data.yml file
	 * 
	 * @param material
	 *            The Material of the {@link StorageCustomItem}.
	 * @param durability
	 *            The Durability Value of the {@link StorageCustomItem}
	 */
	public StorageCustomItem(Material material, short durability) {
		this(DataFile.getInstance().getCustomItem(material, durability));
	}

	/**
	 * Create a new {@link StorageCustomItem}. The values for the Material and
	 * Durability will be generated form the values of the data.yml file
	 * 
	 * @param idName
	 *            The ID Name of the {@link StorageCustomItem}
	 * @param texture
	 *            The Texture Name of the {@link StorageCustomItem}
	 */
	public StorageCustomItem(String idName, String texture) {
		this(DataFile.getInstance().getCustomItem(idName, texture));
	}

	/**
	 * Create a new {@link StorageCustomItem} from another
	 * {@link StorageCustomItem}. The values of the object specified won't be
	 * cloned, they will be the same.
	 * 
	 * @param sItem
	 *            The {@link StorageCustomItem} to clone.
	 */
	public StorageCustomItem(StorageCustomItem sItem) {
		this.setMaterial(sItem.getMaterial());
		this.setDurability(sItem.getDurability());
		this.setIdName(sItem.getIdName());
		this.setTexture(sItem.getTexture());
	}

	/**
	 * @return The {@link Material} of the {@link StorageCustomItem}
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Set the {@link Material} of the {@link StorageCustomItem}.
	 * 
	 * @param material
	 */
	public StorageCustomItem setMaterial(Material material) {
		this.material = material;
		return this;
	}

	/**
	 * @return The {@link StorageCustomItem}'s Durability Value. This is not
	 *         fake durability - it's what durability the item given to the
	 *         player will actually have. This is very important for
	 *         {@link CustomTexturedItem}s where the durability is not
	 *         predetermined, but instead defined upon server startup.
	 * 
	 */
	public short getDurability() {
		return durability;
	}

	/**
	 * Set the {@link StorageCustomItem}'s Durability Value. This is not fake
	 * durability - it's what durability the item given to the player will
	 * actually have. This is very important for {@link CustomTexturedItem}s
	 * where the durability is not predetermined, but instead defined upon
	 * server startup.
	 * 
	 * @param durability
	 */
	public StorageCustomItem setDurability(short durability) {
		this.durability = durability;
		return this;
	}

	/**
	 * @return The result {@link CustomItem}'s ID Name.
	 */
	public String getIdName() {
		return idName;
	}

	/**
	 * Set the result {@link CustomItem}'s ID Name.
	 * 
	 * @param idName
	 */
	public StorageCustomItem setIdName(String idName) {
		this.idName = idName;
		return this;
	}

	/**
	 * @return The name of the texture that is assigned to this
	 *         {@link StorageCustomItem}'s durability point.
	 */
	public String getTexture() {
		return texture;
	}

	/**
	 * Set the name of the texture that is a ssigned to this
	 * {@link StorageCustomItem}'s durability point.
	 * 
	 * @param texture
	 */
	public StorageCustomItem setTexture(String texture) {
		this.texture = texture;
		return this;
	}

	/**
	 * @return Get a {@link CustomItemStack} with this
	 *         {@link StorageCustomItem}'s IdName, Durability and Texture Name.
	 */
	public CustomItemStack getCustomItemStack() {
		return new CustomItemStack(idName, durability, texture);
	}

	/**
	 * @return Get a {@link CustomItem} with this {@link StorageCustomItem}'s
	 *         IdName, Durability and Texture Name.
	 */
	public CustomItem getCustomItem() {
		return getCustomItemStack().getCustomItem();
	}
}
