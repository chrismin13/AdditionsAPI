package us.fihgu.toolbox.resourcepack.model;

/**
 * change the model of an item depends on the state of the item.<br>
 *     Note: a model that is overrode can not be override again,(No nested override)
 */
public class OverrideEntry implements Comparable<OverrideEntry>
{
	/**
	 * A state of the item.
	 */
	protected Predicate predicate;

	/**
	 * the replaced model.
	 */
	protected String model;
	
	public OverrideEntry(Predicate predicate, String model)
	{
		this.predicate = predicate;
		this.model = model;
	}

	/**
	 * See {@link #predicate}
	 */
	public Predicate getPredicate()
	{
		return predicate;
	}

	/**
	 * See {@link #predicate}
	 */
	public void setPredicate(Predicate predicate)
	{
		this.predicate = predicate;
	}

	/**
	 * See {@link #model}
	 */
	public String getModel()
	{
		return model;
	}

	/**
	 * See {@link #model}
	 */
	public void setModel(String model)
	{
		this.model = model;
	}

	@Override
	public OverrideEntry clone()
	{
		return new OverrideEntry(predicate.clone(), model);
	}

	/**
	 * Compares according to the predicate's damage
	 */
	@Override
	public int compareTo(OverrideEntry overrrideEntry) {
		final int before = -1;
		final int equal = 0;
		final int after = 1;

		if (this == overrrideEntry)
			return equal;

		if (this.getPredicate().getDamage() < overrrideEntry.getPredicate().getDamage())
			return before;
		if (this.getPredicate().getDamage() > overrrideEntry.getPredicate().getDamage())
			return after;

		return equal;
	}
}
