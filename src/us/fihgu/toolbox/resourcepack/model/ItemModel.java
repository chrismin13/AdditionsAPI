package us.fihgu.toolbox.resourcepack.model;

import us.fihgu.toolbox.item.DamageableItem;

import java.util.LinkedList;
import java.util.List;

/**
 * A model used for an item
 */
@SuppressWarnings("unused")
public class ItemModel extends Model {
	/**
	 * determine which item model should be used base on the predicate values
	 */
	protected List<OverrideEntry> overrides;

	/**
	 * See {@link #overrides}
	 */
	public List<OverrideEntry> getOverrides() {
		return overrides;
	}

	/**
	 * See {@link #overrides}
	 */
	public void setOverrides(List<OverrideEntry> overrides) {
		this.overrides = overrides;
	}

	/**
	 * See {@link #overrides}<br>
	 * creates a new Override list if one is not present
	 */
	public void addOverride(OverrideEntry entry) {
		if (this.overrides == null) {
			this.overrides = new LinkedList<>();
		}

		this.overrides.add(entry);
	}

	/**
	 * create a simple item model with a parent and texture.
	 */
	public static ItemModel createSimpleItemModel(String parent, String texture) {
		ItemModel model = new ItemModel();
		model.setParent(parent);
		model.putTexture("layer0", texture);
		return model;
	}
}
