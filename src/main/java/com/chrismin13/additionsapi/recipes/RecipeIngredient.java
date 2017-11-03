package com.chrismin13.additionsapi.recipes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.chrismin13.additionsapi.utils.Debug;

public class RecipeIngredient implements Cloneable, Comparable<RecipeIngredient> {

	private Material material;
	private Byte blockData;
	// private Short durability;
	// private String customIdName;

	public RecipeIngredient(Material material) {
		setMaterial(material);
		setBlockData((byte) 0);
	}

	public RecipeIngredient(Material material, byte blockData) {
		setMaterial(material);
		setBlockData(blockData);
	}

	@SuppressWarnings("deprecation")
	public RecipeIngredient(ItemStack itemStack) {
		if (itemStack != null) {
			setMaterial(itemStack.getType());
			setBlockData(itemStack.getData().getData());
		} else {
			setMaterial(null);
			setBlockData(null);
		}

	}

	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @param material
	 *            the material to set
	 */
	public RecipeIngredient setMaterial(Material material) {
		this.material = material;
		return this;
	}

	/**
	 * @return the blockData
	 */
	public byte getBlockData() {
		return blockData;
	}

	/**
	 * @param blockData
	 *            the blockData to set
	 */
	public RecipeIngredient setBlockData(Byte blockData) {
		this.blockData = blockData;
		return this;
	}

	/// **
	// * @return the durability - null if it's a CustomItem.
	// */
	// public Short getDurability() {
	// return durability;
	// }

	/// **
	// * @param durability
	// * the durability to set - null if it's a CustomItem.
	// */
	// public void setDurability(Short durability) {
	// this.durability = durability;
	// }

	/// **
	// * @return the idName - null if it's NOT a CustomItem.
	// */
	// public String getIdName() {
	// return customIdName;
	// }

	/// **
	// * @param idName
	// * the idName to set - null if it's NOT a CustomItem.
	// */
	// public void setidName(String idName) {
	// this.customIdName = idName;
	// }

	@Override
	public RecipeIngredient clone() {
		try {
			return (RecipeIngredient) super.clone();
		} catch (CloneNotSupportedException e) {
			Debug.sayTrueError("Cloning not supported!");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!RecipeIngredient.class.isAssignableFrom(obj.getClass())) {
			return false;
		}
		final RecipeIngredient that = (RecipeIngredient) obj;
		return materialEquals(that) && blockDataEquals(that);
	}

	public boolean materialEquals(RecipeIngredient that) {
		if (this.material == null || that.material == null) {
			if (this.material == that.material) {
				return true;
			}
		} else {
			if (this.material.equals(that.material)) {
				return true;
			}
		}
		return false;
	}

	public boolean blockDataEquals(RecipeIngredient that) {
		if (this.blockData == null || that.blockData == null) {
			if (this.blockData == that.blockData) {
				return true;
			}
		} else {
			if (this.blockData.equals(that.blockData)) {
				return true;
			}
		}
		return false;
	}

	// public boolean durabilityEquals(RecipeIngredient that) {
	// if (this.durability == null || that.durability == null) {
	// if (this.durability == that.durability) {
	// Debug.saySuper("Durability == !" + durability);
	// return true;
	// }
	// } else {
	// if (this.durability.equals(that.durability)) {
	// Debug.saySuper("Durability equal!" + durability);
	// return true;
	// }
	// }
	// return false;
	// }

	// public boolean idNameEquals(RecipeIngredient that) {
	// if (this.customIdName == null || that.customIdName == null) {
	// if (this.customIdName == that.customIdName) {
	// Debug.saySuper("IdName == !" + customIdName);
	// return true;
	// }
	// } else {
	// if (this.customIdName.equals(that.customIdName)) {
	// Debug.saySuper("IdName equal!" + customIdName);
	// return true;
	// }
	// }
	// return false;
	// }

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.material != null ? this.material.hashCode() : 0);
		hash = 53 * hash + (this.blockData != null ? this.blockData.hashCode() : 0);
		// hash = 53 * hash + (this.durability != null ?
		// this.durability.hashCode() : 0);
		// hash = 53 * hash + (this.customIdName != null ?
		// this.customIdName.hashCode() : 0);
		return hash;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(RecipeIngredient ingredient) {
		final int before = -1;
		final int equal = 0;
		final int after = 1;

		if (this == ingredient)
			return equal;

		if (this.material.getId() < ingredient.material.getId())
			return before;
		if (this.material.getId() > ingredient.material.getId())
			return after;

		if (this.blockData < ingredient.blockData)
			return before;
		if (this.blockData > ingredient.blockData)
			return after;

		return equal;
	}
}
