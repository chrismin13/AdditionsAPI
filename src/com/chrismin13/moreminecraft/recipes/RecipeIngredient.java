package com.chrismin13.moreminecraft.recipes;

import org.bukkit.Material;
import com.chrismin13.moreminecraft.utils.Debug;

public class RecipeIngredient implements Cloneable {

	private Material material;
	//private Short durability;
	//private String customIdName;

	public RecipeIngredient(Material material) {
		setMaterial(material);
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
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	///**
	// * @return the durability - null if it's a CustomItem.
	// */
	//public Short getDurability() {
	//	return durability;
	//}

	///**
	// * @param durability
	// *            the durability to set - null if it's a CustomItem.
	// */
	//public void setDurability(Short durability) {
	//	this.durability = durability;
	//}

	///**
	// * @return the customItemIdName - null if it's NOT a CustomItem.
	// */
	//public String getIdName() {
	//	return customIdName;
	//}

	///**
	// * @param customItemIdName
	// *            the customItemIdName to set - null if it's NOT a CustomItem.
	// */
	//public void setCustomItemIdName(String customItemIdName) {
	//	this.customIdName = customItemIdName;
	//}

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
		return materialEquals(that);
		//return (materialEquals(that) && durabilityEquals(that) && customItemIdNameEquals(that));
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

	//public boolean durabilityEquals(RecipeIngredient that) {
	//	if (this.durability == null || that.durability == null) {
	//		if (this.durability == that.durability) {
	//			Debug.saySuper("Durability == !" + durability);
	//			return true;
	//		}
	//	} else {
	//		if (this.durability.equals(that.durability)) {
	//			Debug.saySuper("Durability equal!" + durability);
	//			return true;
	//		}
	//	}
	//	return false;
	//}

	//public boolean customItemIdNameEquals(RecipeIngredient that) {
	//	if (this.customIdName == null || that.customIdName == null) {
	//		if (this.customIdName == that.customIdName) {
	//			Debug.saySuper("IdName == !" + customIdName);
	//			return true;
	//		}
	//	} else {
	//		if (this.customIdName.equals(that.customIdName)) {
	//			Debug.saySuper("IdName equal!" + customIdName);
	//			return true;
	//		}
	//	}
	//	return false;
	//}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 53 * hash + (this.material != null ? this.material.hashCode() : 0);
		//hash = 53 * hash + (this.durability != null ? this.durability.hashCode() : 0);
		//hash = 53 * hash + (this.customIdName != null ? this.customIdName.hashCode() : 0);
		return hash;
	}

}
