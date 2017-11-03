package us.fihgu.toolbox.resourcepack.model;

import java.util.Arrays;

/**
 * Contains the properties of the specified face.
 */
public class ElementFaceEntry
{
    /**
     * Defines the area of the texture to use according to the scheme [x1, y1, x2, y2]. If unset, it defaults to values equal to xyz position of the element. The texture behavior will be inconsistent if UV extends below 0 or above 16. If the x or y number are swapped, the texture will be flipped in that direction. UV is optional, and if not supplied it will automatically generate based on the element's position.
     */
    protected double[] uv;

    /**
     * Specifies the texture in form of the texture variable prepended with a #.
     */
    protected String texture;

    /**
     * Specifies whether a face does not need to be rendered when there is a block touching it in the specified position. The position can be: down, up, north, south, west, or east. It will also determine which side of the block to use the light level from for lighting the face, and if unset, defaults to the side.
     */
    protected ModelFace cullface;

    /**
     * <p>Block: Rotates the texture by the specified number of degrees. Can be 0, 90, 180, or 270. Defaults to 0. Rotation does not affect which part of the texture is used. Instead, it amounts to permutation of the selected texture vertexes (selected implicitly, or explicitly though uv).</p>
     * <p>Item: Rotates the texture in increments of 90 degrees.</p>
     */
    protected Double rotation;

    /**
     * Determines whether to tint the texture using a hardcoded tint index. The default is not using the tint, and any number causes it to use tint. Note that only certain blocks have a tint index, all others will be unaffected.
     */
    protected Integer tintindex;

    public ElementFaceEntry()
    {

    }

    /**
     * @param uv See {@link #uv}
     * @param texture See {@link #texture}
     * @param cullface See {@link #cullface}
     * @param rotation See {@link #rotation}
     * @param tintindex See {@link #tintindex}
     *
     *                  @throws IllegalArgumentException when the format of uv is wrong.
     */
    public ElementFaceEntry(double[] uv, String texture, ModelFace cullface, Double rotation, Integer tintindex)
    {
        this.setUv(uv);
        this.setTexture(texture);
        this.setCullface(cullface);
        this.setRotation(rotation);
        this.setTintindex(tintindex);
    }

    /**
     * See {@link #uv}
     */
    public double[] getUv()
    {
        return uv;
    }

    /**
     * See {@link #uv}
     */
    public void setUv(double[] uv)
    {
        if(uv != null && uv.length != 4)
        {
            throw new IllegalArgumentException("uv expected [x1, y1, x2, y2], but got " + Arrays.toString(uv) + " instead.");
        }

        this.uv = uv;
    }

    /**
     * See {@link #texture}
     */
    public String getTexture()
    {
        return texture;
    }

    /**
     * See {@link #texture}
     */
    public void setTexture(String texture)
    {
        this.texture = texture;
    }

    /**
     * See {@link #cullface}
     */
    public ModelFace getCullface()
    {
        return cullface;
    }

    /**
     * See {@link #cullface}
     */
    public void setCullface(ModelFace cullface)
    {
        this.cullface = cullface;
    }

    /**
     * See {@link #rotation}
     */
    public Double getRotation()
    {
        return rotation;
    }

    /**
     * See {@link #rotation}
     */
    public void setRotation(Double rotation)
    {
        this.rotation = rotation;
    }

    /**
     * See {@link #tintindex}
     */
    public Integer getTintindex()
    {
        return tintindex;
    }

    /**
     * See {@link #tintindex}
     */
    public void setTintindex(Integer tintindex)
    {
        this.tintindex = tintindex;
    }
}
