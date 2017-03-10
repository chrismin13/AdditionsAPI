package us.fihgu.toolbox.resourcepack.model;

/**
 * states of an item
 */
public class Predicate {
	/**
	 * Used on compasses to determine the current angle, expressed in a decimal
	 * value of less than one.
	 */
	protected Double angle;

	/**
	 * Used on shields to determine if currently blocking. If 1, the player is
	 * blocking.
	 */
	protected Integer blocking;

	/**
	 * Used on Elytra to determine if broken. If 1, the Elytra is broken.
	 */
	protected Integer broken;

	/**
	 * Used on fishing rods to determine if the fishing rod has been cast. If 1,
	 * the fishing rod has been cast.
	 */
	protected Integer cast;

	/**
	 * Used on ender pearls and chorus fruit to determine the remaining
	 * cooldown, expressed in a decimal value between 0 and 1.
	 */
	protected Double cooldown;

	/**
	 * Used on items with durability to determine the amount of damage,
	 * expressed in a decimal value between 0 and 1.
	 */
	protected Double damage;

	/**
	 * Used on items with durability to determine if it is damaged. If 1, the
	 * item is damaged. Note that if an item has the unbreakable tag, this may
	 * be 0 while the item has a non-zero "damage" tag.
	 */
	protected Integer damaged;

	/**
	 * Determines the model used by left handed players. It will affect the item
	 * they see in inventories, along with the item players see them holding or
	 * wearing.
	 */
	protected Integer lefthanded;

	/**
	 * Determines the amount a bow has been pulled, expressed in a decimal value
	 * of less than one.
	 */
	protected Double pull;

	/**
	 * Used on bows to determine if the bow is being pulled. If 1, the bow is
	 * currently being pulled.
	 */
	protected Integer pulling;

	/**
	 * Used on clocks to determine the current time, expressed in a decimal
	 * value of less than one.
	 */
	protected Double time;

	/**
	 * See {@link #angle}
	 */
	public Double getAngle() {
		return angle;
	}

	/**
	 * See {@link #angle}
	 */
	public Predicate setAngle(Double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * See {@link #blocking}
	 */
	public Integer getBlocking() {
		return blocking;
	}

	/**
	 * See {@link #blocking}
	 */
	public Predicate setBlocking(Integer blocking) {
		this.blocking = blocking;
		return this;
	}

	/**
	 * See {@link #broken}
	 */
	public Integer getBroken() {
		return broken;
	}

	/**
	 * See {@link #broken}
	 */
	public Predicate setBroken(Integer broken) {
		this.broken = broken;
		return this;
	}

	/**
	 * See {@link #cast}
	 */
	public Integer getCast() {
		return cast;
	}

	/**
	 * See {@link #cast}
	 */
	public Predicate setCast(Integer cast) {
		this.cast = cast;
		return this;
	}

	/**
	 * See {@link #cooldown}
	 */
	public Double getCooldown() {
		return cooldown;
	}

	/**
	 * See {@link #cooldown}
	 */
	public Predicate setCooldown(Double cooldown) {
		this.cooldown = cooldown;
		return this;
	}

	/**
	 * See {@link #damage}
	 */
	public Double getDamage() {
		return damage;
	}

	/**
	 * See {@link #damage}
	 */
	public Predicate setDamage(Double damage) {
		this.damage = damage;
		return this;
	}

	/**
	 * See {@link #damaged}
	 */
	public Integer getDamaged() {
		return damaged;
	}

	/**
	 * See {@link #damaged}
	 */
	public Predicate setDamaged(Integer damaged) {
		this.damaged = damaged;
		return this;
	}

	/**
	 * See {@link #lefthanded}
	 */
	public Integer getLefthanded() {
		return lefthanded;
	}

	/**
	 * See {@link #lefthanded}
	 */
	public Predicate setLefthanded(Integer lefthanded) {
		this.lefthanded = lefthanded;
		return this;
	}

	/**
	 * See {@link #pull}
	 */
	public Double getPull() {
		return pull;
	}

	/**
	 * See {@link #pull}
	 */
	public Predicate setPull(Double pull) {
		this.pull = pull;
		return this;
	}

	/**
	 * See {@link #pulling}
	 */
	public Integer getPulling() {
		return pulling;
	}

	/**
	 * See {@link #pulling}
	 */
	public Predicate setPulling(Integer pulling) {
		this.pulling = pulling;
		return this;
	}

	/**
	 * See {@link #time}
	 */
	public Double getTime() {
		return time;
	}

	/**
	 * See {@link #time}
	 */
	public Predicate setTime(Double time) {
		this.time = time;
		return this;
	}

	@Override
	protected Predicate clone() {
		return new Predicate().setDamage(damage).setDamaged(damaged).setAngle(angle).setBlocking(blocking)
				.setBroken(broken).setCast(cast).setPull(pull).setPulling(pulling).setCooldown(cooldown)
				.setLefthanded(lefthanded).setTime(time);
	}
}
