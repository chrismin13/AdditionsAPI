package us.fihgu.toolbox.resourcepack.model;

/**
 * Holds all the faces of the cube. If a face is left out, it will not be rendered.
 */
public class ElementFaceOptions
{
    protected ElementFaceEntry down;
    protected ElementFaceEntry up;
    protected ElementFaceEntry north;
    protected ElementFaceEntry south;
    protected ElementFaceEntry west;
    protected ElementFaceEntry east;

    public ElementFaceEntry getDown()
    {
        return down;
    }

    public void setDown(ElementFaceEntry down)
    {
        this.down = down;
    }

    public ElementFaceEntry getUp()
    {
        return up;
    }

    public void setUp(ElementFaceEntry up)
    {
        this.up = up;
    }

    public ElementFaceEntry getNorth()
    {
        return north;
    }

    public void setNorth(ElementFaceEntry north)
    {
        this.north = north;
    }

    public ElementFaceEntry getSouth()
    {
        return south;
    }

    public void setSouth(ElementFaceEntry south)
    {
        this.south = south;
    }

    public ElementFaceEntry getWest()
    {
        return west;
    }

    public void setWest(ElementFaceEntry west)
    {
        this.west = west;
    }

    public ElementFaceEntry getEast()
    {
        return east;
    }

    public void setEast(ElementFaceEntry east)
    {
        this.east = east;
    }
}
