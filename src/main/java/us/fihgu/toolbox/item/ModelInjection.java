package us.fihgu.toolbox.item;

import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.Predicate;

/**
 * Used for injecting custom item model.
 */
public class ModelInjection {
	/**
	 * when to use this model, can be an empty instance, but should never be
	 * null.
	 */
	private Predicate predicate;

	/**
	 * The name of the model (ex: 'item/example'), if the same name is already
	 * used, the older model will be overrode.
	 * <p>
	 * The best way to ensure your model name is unique is to prefix it with
	 * your plugin's name
	 * </p>
	 */
	private String modelName;

	/**
	 * The model to use.
	 * <p>
	 * Hint: use {@link ItemModel#createSimpleItemModel(String, String)} to
	 * quickly create a model.
	 * </p>
	 */
	private ItemModel model;

	/**
	 * @param predicate
	 *            See {@link #predicate}
	 * @param modelName
	 *            See {@link #modelName}
	 * @param model
	 *            See {@link #model}
	 */
	public ModelInjection(Predicate predicate, String modelName, ItemModel model) {
		this.predicate = predicate;
		this.modelName = modelName;
		this.model = model;
	}

	/**
	 * @return See {@link #predicate}
	 */
	public Predicate getPredicate() {
		return predicate;
	}

	/**
	 * @return See {@link #modelName}
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @return See {@link #model}
	 */
	public ItemModel getModel() {
		return model;
	}
}
