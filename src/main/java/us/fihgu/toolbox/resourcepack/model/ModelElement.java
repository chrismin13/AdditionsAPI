package us.fihgu.toolbox.resourcepack.model;

import java.util.Arrays;

/**
 * An element that comprise a model.
 */
public class ModelElement
{
    /**
     * Start point of a cube according to the scheme [x, y, z]. Values must be between -16 and 32.
     */
    protected double[] from;

    /**
     * Stop point of a cube according to the scheme [x, y, z]. Values must be between -16 and 32.
     */
    protected double[] to;

    /**
     * Defines the rotation of an element.
     */
    protected ElementRotation rotation;

    /**
     * Defines if shadows are rendered (true - default), not (false).
     */
    protected Boolean shade;

    /**
     * Holds all the faces of the cube. If a face is left out, it will not be rendered.
     */
    protected ElementFaceOptions faces;

    public ModelElement()
    {}

    /**
     * @throws  IllegalArgumentException when from or to has the wrong format.
     * @param from See {@link #from}
     * @param to See {@link #to}
     * @param rotation See {@link #rotation}
     * @param shade See {@link #shade}
     * @param faces See {@link #faces}
     */
    public ModelElement(double[] from, double[] to, ElementRotation rotation, Boolean shade, ElementFaceOptions faces)
    {
        this.setFrom(from);
        this.setTo(to);
        this.setRotation(rotation);
        this.setShade(shade);
        this.setFaces(faces);
    }

    /**
     * See {@link #from}
     */
    public double[] getFrom()
    {
        return from;
    }

    /**
     * See {@link #from}
     */
    public void setFrom(double[] from)
    {
        if(from != null && from.length != 3)
        {
            throw new IllegalArgumentException("from expected [x, y, z], but got " + Arrays.toString(from) + " instead.");
        }

        this.from = from;
    }

    /**
     * See {@link #to}
     */
    public double[] getTo()
    {
        return to;
    }

    /**
     * See {@link #to}
     */
    public void setTo(double[] to)
    {
        if(to != null && to.length != 3)
        {
            throw new IllegalArgumentException("to expected [x, y, z], but got " + Arrays.toString(to) + " instead.");
        }

        this.to = to;
    }

    /**
     * See {@link #rotation}
     */
    public ElementRotation getRotation()
    {
        return rotation;
    }

    /**
     * See {@link #rotation}
     */
    public void setRotation(ElementRotation rotation)
    {
        this.rotation = rotation;
    }

    /**
     * See {@link #shade}
     */
    public Boolean getShade()
    {
        return shade;
    }

    /**
     * See {@link #shade}
     */
    public void setShade(Boolean shade)
    {
        this.shade = shade;
    }

    /**
     * See {@link #faces}
     */
    public ElementFaceOptions getFaces()
    {
        return faces;
    }

    /**
     * See {@link #faces}
     */
    public void setFaces(ElementFaceOptions faces)
    {
        this.faces = faces;
    }
}
