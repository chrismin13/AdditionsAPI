package us.fihgu.toolbox.item;

import us.fihgu.toolbox.json.JsonUtils;
import us.fihgu.toolbox.resourcepack.model.DisplayEntry;
import us.fihgu.toolbox.resourcepack.model.DisplayOptions;
import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.OverrideEntry;
import us.fihgu.toolbox.resourcepack.model.Predicate;

import java.io.File;
import java.util.HashMap;

import com.chrismin13.additionsapi.items.CustomItem;
import com.chrismin13.additionsapi.items.textured.CustomTexturedItem;

/**
 * Can be implemented by a {@link CustomItem} or a class that extends the
 * {@link CustomItem}. Otherwise, it will not be able to inject the textures,
 * unless the {@link #inject(ItemModel, File)} is overriden with your own
 * code.<br>
 * <p>
 * This interface will allow you to define your own model for your custom item.
 * <br>
 * Created by fihgu, improved by chrismin13.
 * </p>
 * 
 * @author fihgu
 * @see CustomTexturedItem
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
	HashMap<ModelInjection, Short> getOverrideEntries();

	/**
	 * This is needed for later use and will be filled with Texture Names when
	 * using {@link #addTexture(String)} and with the appropriate durability
	 * values when the API is enabled. Here's some example code: <br>
	 * <code>private Map<String, Short> overrideModels = new HashMap<String, Short>();
	
	&#64;Override
	public Map<String, Short> getAllTextures() {
		return overrideModels;
	}</code>
	 */
	HashMap<String, Short> getAllTextures();

	/**
	 * Add a Texture Name that will be included with the Textured Item.
	 * 
	 * @param texture
	 *            The Texture Name.
	 */
	default void addTexture(String texture) {
		getAllTextures().put(texture, null);
	}

	/**
	 * <b>This is only valid after the API has been enabled. Otherwise, it will
	 * be null.</b><br>
	 * Get the Texture Name that is assigned to the Durability Value that you
	 * define.
	 * 
	 * @param durability
	 *            The durability Value.
	 * @return The Texture Name.
	 */
	default String getTexture(short durability) {
		for (String texture : getAllTextures().keySet()) {
			if (getAllTextures().get(texture) == durability)
				return texture;
		}
		return null;
	}

	/**
	 * @return The Default Texture that the Textured Item will have. This must
	 *         be defined upon creating the Java Object and never be changed
	 *         during the Object's lifetime.
	 */
	String getDefaultTexture();

	/**
	 * Check if this instancce of ModelInjector is a CustomItem.
	 */
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
		String[] name = item.getIdName().split(":");
		double maxDurability = (double) getDamageableItem().getMaxDurability();

		final HashMap<ModelInjection, Short> overrideEntries = this.getOverrideEntries();

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

			// add the bow model if needed
			if (entry instanceof BowModelInjection && ((BowModelInjection) entry).isStandby()) {
				DisplayOptions bowDisplay = new DisplayOptions();
				bowDisplay.setThirdperson_righthand(new DisplayEntry(new double[] { -80, 260, -40 },
						new double[] { -1, -2, 2.5 }, new double[] { 0.9, 0.9, 0.9 }));
				bowDisplay.setThirdperson_lefthand(new DisplayEntry(new double[] { -80, -280, 40 },
						new double[] { -1, -2, 2.5 }, new double[] { 0.9, 0.9, 0.9 }));
				bowDisplay.setFirstperson_righthand(new DisplayEntry(new double[] { 0, -90, 25 },
						new double[] { 1.13, 3.2, 1.13 }, new double[] { 0.68, 0.68, 0.68 }));
				bowDisplay.setFirstperson_lefthand(new DisplayEntry(new double[] { 0, 90, -25 },
						new double[] { 1.13, 3.2, 1.13 }, new double[] { 0.68, 0.68, 0.68 }));
				entry.getModel().setDisplay(bowDisplay);
			}

			// add entry into work space
			File json = new File(workspace,
					"assets/" + name[0] + "/models/item/" + entry.getModelName().split("/", 2)[1] + ".json");
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
