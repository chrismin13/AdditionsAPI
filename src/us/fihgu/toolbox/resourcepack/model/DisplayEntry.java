package us.fihgu.toolbox.resourcepack.model;


import java.util.Arrays;

/**
 * Describes the rotation, translation, scale of a model in a certain perspective.<br>
 *     Note: translations are applied to the model before rotations
 */
public class DisplayEntry
{
    /**
     * Specifies the rotation of the model according to the scheme [x, y, z].
     */
    private double[] rotation;

    /**
     * Specifies the position of the model according to the scheme [x, y, z].<br>
     * If the value is greater than 80, it is displayed as 80.<br>
     * If the value is less then -80, it is displayed as -80.<br>
     */
    private double[] translation;

    /**
     * Specifies the scale of the model according to the scheme [x, y, z].<br>
     * If the value is greater than 4, it is displayed as 4.
     */
    private double[] scale;

    /**
     * @return {@link #rotation}
     */
    public double[] getRotation()
    {
        return rotation;
    }

    /**
     * @throws IllegalArgumentException if given array has a length other than 3.
     *
     * @param rotation See {@link #rotation}
     * @param translation See {@link #translation}
     * @param scale See {@link #scale}
     */
    public DisplayEntry(double[] rotation, double[] translation, double[] scale)
    {
        this.setRotation(rotation);
        this.setTranslation(translation);
        this.setScale(scale);
    }

    public DisplayEntry()
    {

    }

    /**
     * @param rotation {@link #rotation}
     * @throws IllegalArgumentException if given array has a length other than 3.
     */
    public void setRotation(double[] rotation)
    {
        if(rotation != null && rotation.length != 3)
        {
            throw new IllegalArgumentException("rotation expected: [x, y, z], but got " + Arrays.toString(rotation) + " instead.");
        }

        this.rotation = rotation;
    }

    /**
     * @return {@link #translation}
     */
    public double[] getTranslation()
    {
        return translation;
    }

    /**
     * @param translation {@link #translation}
     * @throws IllegalArgumentException if given array has a length other than 3.
     */
    public void setTranslation(double[] translation)
    {
        if(translation != null && translation.length != 3)
        {
            throw new IllegalArgumentException("translation expected: [x, y, z], but got " + Arrays.toString(translation) + " instead.");
        }

        this.translation = translation;
    }

    /**
     * @return {@link #scale}
     */
    public double[] getScale()
    {
        return scale;
    }

    /**
     * @param scale {@link #scale}
     * @throws IllegalArgumentException if given array has a length other than 3.
     */
    public void setScale(double[] scale)
    {
        if(scale != null && scale.length != 3)
        {
            throw new IllegalArgumentException("scale expected: [x, y, z], but got " + Arrays.toString(scale) + " instead.");
        }

        this.scale = scale;
    }
}
