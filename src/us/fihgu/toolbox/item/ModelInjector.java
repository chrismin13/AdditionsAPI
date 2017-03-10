package us.fihgu.toolbox.item;

import us.fihgu.toolbox.json.JsonUtils;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.OverrideEntry;
import us.fihgu.toolbox.resourcepack.model.Predicate;

import java.io.File;
import java.util.Map;

import com.chrismin13.moreminecraft.api.items.CustomItem;

/**
 * Can be implemented by a {@link CustomItem}<br>
 * <p>
 * This interface will allow you to define your own model for your custom item.
 * </p>
 */
public interface ModelInjector {
	/**
	 * <p>
	 * Provide your override entry to define the model that you want to use.<br>
	 * The "damaged" and "damage" tag will be automatically defined by the
	 * toolbox, you don't need to worry about them.<br>
	 * </p>
	 * <p>
	 * <b>Note: You should at least provide an entry with a empty predicate tag
	 * that points to your basic model</b>
	 * </p>
	 * 
	 * @return A list of override entries that need to be injected into the base
	 *         model.
	 *         <p>
	 *         -- The base model is the original item's model, we inject a
	 *         override entry so it will display the model we defined when an
	 *         itemstack matches our custom item id.
	 *         </p>
	 */
	Map<ModelInjection, Short> getOverrideEntries();

	Map<String, Short> getAllTextures();
	
	default void addTexture(String texture) {
		getAllTextures().put(texture, null);
	}

	default String getTexture(short durability) {
		for (String texture : getAllTextures().keySet()) {
			if (getAllTextures().get(texture) == durability)
				return texture;
		}
		return null;
	}

	String getDefaultTexture();

	default void checkCustomItem() {
		if (!(this instanceof CustomItem)) {
			throw new RuntimeException("ModelInjector implemented by a class other than CustomItem.");
		}
	}

	/**
	 * Inject the override entries into the base model. <br>
	 * Also put custom models into work space.
	 */
	default void inject(ItemModel baseModel, File workspace) {
		checkCustomItem();

		CustomItem item = (CustomItem) this;
		String[] name = item.getCustomItemIdName().split(":");
		double maxDurability = (double) getDamageableItem().getMaxDurability();

		Map<ModelInjection, Short> overrideEntries = this.getOverrideEntries();
		
		for (ModelInjection entry : overrideEntries.keySet()) {

			short durability = overrideEntries.get(entry);

			// add id into predicate value
			Predicate predicate = entry.getPredicate();
			if (item.isUnbreakable())
				predicate.setDamaged(0);
			else
				predicate.setDamaged(1);
			predicate.setDamage((double) durability / maxDurability);

			// inject this entry.
			baseModel.addOverride(new OverrideEntry(entry.getPredicate(), entry.getModelName()));

			// add entry into work space
			File json = new File(workspace,
					"assets/" + name[0] + "/models/item/" + this.getTexture(durability) + ".json");
			if (!json.exists())
				JsonUtils.toFile(json, entry.getModel());
		}
	}

	/**
	 * Get the DamageableItem of the CustomTexturedItem. This value cannot be
	 * changed.
	 * 
	 * @return DamageableItem
	 */
	default DamageableItem getDamageableItem() {
		checkCustomItem();
		return DamageableItem.getDamageableItem(((CustomItem) this).getMaterial());
	}

}
