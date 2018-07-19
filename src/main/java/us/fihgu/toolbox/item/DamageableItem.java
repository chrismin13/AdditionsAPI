package us.fihgu.toolbox.item;

import java.util.EnumSet;

import org.bukkit.Material;

import com.chrismin13.additionsapi.items.textured.CustomTexturedItem;
import com.chrismin13.additionsapi.utils.MaterialUtils;

import us.fihgu.toolbox.resourcepack.model.DisplayEntry;
import us.fihgu.toolbox.resourcepack.model.DisplayOptions;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.OverrideEntry;
import us.fihgu.toolbox.resourcepack.model.Predicate;

/**
 * An item with damage value.<br>
 * Used for identifying {@link CustomTexturedItem}<br>
 */
public enum DamageableItem {
	WOODEN_HOE(Material.WOODEN_HOE, true), STONE_HOE, IRON_HOE, GOLDEN_HOE(Material.GOLDEN_HOE, true), DIAMOND_HOE,

	WOODEN_AXE(Material.WOODEN_AXE, true), STONE_AXE, IRON_AXE, GOLDEN_AXE(Material.GOLDEN_AXE, true), DIAMOND_AXE,

	WOODEN_SWORD(Material.WOODEN_SWORD, true), STONE_SWORD, IRON_SWORD, GOLDEN_SWORD(Material.GOLDEN_SWORD,
			true), DIAMOND_SWORD,

	WOODEN_PICKAXE(Material.WOODEN_PICKAXE, true), STONE_PICKAXE, IRON_PICKAXE, GOLDEN_PICKAXE(Material.GOLDEN_PICKAXE,
			true), DIAMOND_PICKAXE,

	WOODEN_SHOVEL(Material.WOODEN_SHOVEL, "wood_shovel"), STONE_SHOVEL(Material.STONE_SHOVEL), IRON_SHOVEL(
			Material.IRON_SHOVEL), GOLDEN_SHOVEL(Material.GOLDEN_SHOVEL, "gold_shovel"), DIAMOND_SHOVEL(Material.DIAMOND_SHOVEL),

	LEATHER_HELMET, GOLDEN_HELMET(Material.GOLDEN_HELMET, true), CHAINMAIL_HELMET, IRON_HELMET, DIAMOND_HELMET,

	LEATHER_CHESTPLATE, GOLDEN_CHESTPLATE(Material.GOLDEN_CHESTPLATE,
			true), CHAINMAIL_CHESTPLATE, IRON_CHESTPLATE, DIAMOND_CHESTPLATE,

	LEATHER_LEGGINGS, GOLDEN_LEGGINGS(Material.GOLDEN_LEGGINGS,
			true), CHAINMAIL_LEGGINGS, IRON_LEGGINGS, DIAMOND_LEGGINGS,

	LEATHER_BOOTS, GOLDEN_BOOTS(Material.GOLDEN_BOOTS, true), CHAINMAIL_BOOTS, IRON_BOOTS, DIAMOND_BOOTS,

	SHEARS, FISHING_ROD, CARROT_ON_A_STICK(Material.CARROT_ON_A_STICK), FLINT_AND_STEEL, BOW, ELYTRA;

	private Material material;
	private String textureName;

	DamageableItem() {
		this.material = Material.valueOf(this.toString());
		this.textureName = this.toString();
	}

	DamageableItem(Material material) {
		this.material = material;
		this.textureName = this.toString();
	}
	
	DamageableItem(Material material, String textureName) {
		this.material = material;
		this.textureName = textureName;
	}

	DamageableItem(Material material, boolean textureFromMaterial) {
		this.material = material;
		if (textureFromMaterial)
			this.textureName = material.toString().toLowerCase();
		else
			this.textureName = getModelName();
	}

	/**
	 * Damageable items that inherit from the handheld model.
	 */
	public static EnumSet<DamageableItem> handHeldItems = EnumSet.of(WOODEN_HOE, STONE_HOE, IRON_HOE, GOLDEN_HOE,
			DIAMOND_HOE, WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE, WOODEN_SWORD, STONE_SWORD,
			IRON_SWORD, GOLDEN_SWORD, DIAMOND_SWORD, WOODEN_PICKAXE, STONE_PICKAXE, IRON_PICKAXE, GOLDEN_PICKAXE,
			DIAMOND_PICKAXE, WOODEN_SHOVEL, STONE_SHOVEL, IRON_SHOVEL, GOLDEN_SHOVEL, DIAMOND_SHOVEL);

	/**
	 * get the default item model of this damageable item.
	 */

	public ItemModel getDefaultItemModel() {
		switch (this) {
		case BOW:
			ItemModel bowModel = new ItemModel();
			bowModel.setParent("item/generated");
			bowModel.putTexture("layer0", "items/bow_standby");
			DisplayOptions bowDisplay = new DisplayOptions();
			bowDisplay.setThirdperson_righthand(new DisplayEntry(new double[] { -80, 260, -40 },
					new double[] { -1, -2, 2.5 }, new double[] { 0.9, 0.9, 0.9 }));
			bowDisplay.setThirdperson_lefthand(new DisplayEntry(new double[] { -80, -280, 40 },
					new double[] { -1, -2, 2.5 }, new double[] { 0.9, 0.9, 0.9 }));
			bowDisplay.setFirstperson_righthand(new DisplayEntry(new double[] { 0, -90, 25 },
					new double[] { 1.13, 3.2, 1.13 }, new double[] { 0.68, 0.68, 0.68 }));
			bowDisplay.setFirstperson_lefthand(new DisplayEntry(new double[] { 0, 90, -25 },
					new double[] { 1.13, 3.2, 1.13 }, new double[] { 0.68, 0.68, 0.68 }));
			bowModel.setDisplay(bowDisplay);
			bowModel.addOverride(new OverrideEntry(new Predicate().setPulling(1), "item/bow_pulling_0"));
			bowModel.addOverride(new OverrideEntry(new Predicate().setPulling(1).setPull(0.65), "item/bow_pulling_1"));
			bowModel.addOverride(new OverrideEntry(new Predicate().setPulling(1).setPull(0.9), "item/bow_pulling_2"));
			return bowModel;
		case FISHING_ROD:
			ItemModel fishingRodModel = new ItemModel();
			fishingRodModel.setParent("item/handheld_rod");
			fishingRodModel.putTexture("layer0", "items/fishing_rod_uncast");
			fishingRodModel.addOverride(new OverrideEntry(new Predicate().setCast(1), "item/fishing_rod_cast"));
			return fishingRodModel;
		case CARROT_ON_A_STICK:
			return ItemModel.createSimpleItemModel("item/handheld_rod", "items/carrot_on_a_stick");
		case ELYTRA:
			ItemModel elytraRodModel = new ItemModel();
			elytraRodModel.setParent("item/generated");
			elytraRodModel.putTexture("layer0", "items/elytra");
			elytraRodModel.addOverride(new OverrideEntry(new Predicate().setBroken(1), "item/broken_elytra"));
			return elytraRodModel;
		default:
			if (DamageableItem.handHeldItems.contains(this)) {
				return ItemModel.createSimpleItemModel("item/handheld", "items/" + this.getTextureName().toLowerCase());
			} else {
				return ItemModel.createSimpleItemModel("item/generated", "items/" + this.getTextureName().toLowerCase());
			}
		}
	}

	public Material getMaterial() {
		return material;
	}

	public int getMaxDurability() {
		return material.getMaxDurability();
	}

	public String getModelName() {
		return this.name().toLowerCase();
	}

	public String getTextureName() {
		return textureName;
	}

	public static DamageableItem getDamageableItem(Material material) {
		for (DamageableItem damageableItem : DamageableItem.values())
			if (material == damageableItem.getMaterial())
				return damageableItem;
		return null;
	}

	public float getAttackSpeed() {
		return MaterialUtils.getBaseSpeed(getMaterial());
	}

	public float getAttackDamage() {
		return MaterialUtils.getBaseDamage(getMaterial());
	}

}
