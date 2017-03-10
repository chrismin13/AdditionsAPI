package us.fihgu.toolbox.resourcepack.model;

/**
 * describes how a {@link ModelElement} rotates.
 */
public class ElementRotation
{
    /**
     * Sets the center of the rotation according to the scheme [x, y, z], defaults to [8, 8, 8].
     */
    protected double[] origin;

    /**
     * Specifies the direction of rotation
     */
    protected ModelAxis axis;

    /**
     * Specifies the angle of rotation. Can be 45 through -45 degrees in 22.5 degree increments. Defaults to 0.
     */
    protected Double angle;

    /**
     * Specifies whether or not to scale the faces across the whole block. Can be true or false. Defaults to false.
     */
    protected Boolean rescale;

    public ElementRotation()
    {
    }

    /**
     * @param origin See {@link #origin}
     * @param axis See {@link #axis}
     * @param angle See {@link #angle}
     * @param rescale See {@link #rescale}
     */
    public ElementRotation(double[] origin, ModelAxis axis, Double angle, Boolean rescale)
    {
        this.setOrigin(origin);
        this.setAxis(axis);
        this.setAngle(angle);
        this.setRescale(rescale);
    }

    /**
     * See {@link #origin}
     */
    public double[] getOrigin()
    {
        return origin;
    }

    /**
     * See {@link #origin}
     */
    public void setOrigin(double[] origin)
    {
        this.origin = origin;
    }

    /**
     * See {@link #axis}
     */
    public ModelAxis getAxis()
    {
        return axis;
    }

    /**
     * See {@link #axis}
     */
    public void setAxis(ModelAxis axis)
    {
        this.axis = axis;
    }

    /**
     * See {@link #angle}
     */
    public Double getAngle()
    {
        return angle;
    }

    /**
     * See {@link #angle}
     */
    public void setAngle(Double angle)
    {
        this.angle = angle;
    }

    /**
     * See {@link #rescale}
     */
    public Boolean getRescale()
    {
        return rescale;
    }

    /**
     * See {@link #rescale}
     */
    public void setRescale(Boolean rescale)
    {
        this.rescale = rescale;
    }
}
