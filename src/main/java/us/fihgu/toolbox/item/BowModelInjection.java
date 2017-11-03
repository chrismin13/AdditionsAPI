package us.fihgu.toolbox.item;

import us.fihgu.toolbox.resourcepack.model.ItemModel;
import us.fihgu.toolbox.resourcepack.model.Predicate;

public class BowModelInjection extends ModelInjection {

	private boolean isStandby;
	
	public BowModelInjection(Predicate predicate, String modelName, ItemModel model, boolean isStandby) {
		super(predicate, modelName, model);
		this.setStandby(isStandby);
	}

	/**
	 * @return the isStandby
	 */
	public boolean isStandby() {
		return isStandby;
	}

	/**
	 * @param isStandby the isStandby to set
	 */
	public BowModelInjection setStandby(boolean isStandby) {
		this.isStandby = isStandby;
		return this;
	}

}
