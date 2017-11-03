package us.fihgu.toolbox.resourcepack.model;

/**
 * Defines how this model look in different perspectives
 */
public class DisplayOptions
{
    private DisplayEntry thirdperson_righthand;
    private DisplayEntry thirdperson_lefthand;
    private DisplayEntry firstperson_righthand;
    private DisplayEntry firstperson_lefthand;
    private DisplayEntry gui;
    private DisplayEntry head;
    private DisplayEntry ground;
    private DisplayEntry fixed;

    public DisplayEntry getThirdperson_righthand()
    {
        return thirdperson_righthand;
    }

    public void setThirdperson_righthand(DisplayEntry thirdperson_righthand)
    {
        this.thirdperson_righthand = thirdperson_righthand;
    }

    public DisplayEntry getThirdperson_lefthand()
    {
        return thirdperson_lefthand;
    }

    public void setThirdperson_lefthand(DisplayEntry thirdperson_lefthand)
    {
        this.thirdperson_lefthand = thirdperson_lefthand;
    }

    public DisplayEntry getFirstperson_righthand()
    {
        return firstperson_righthand;
    }

    public void setFirstperson_righthand(DisplayEntry firstperson_righthand)
    {
        this.firstperson_righthand = firstperson_righthand;
    }

    public DisplayEntry getFirstperson_lefthand()
    {
        return firstperson_lefthand;
    }

    public void setFirstperson_lefthand(DisplayEntry firstperson_lefthand)
    {
        this.firstperson_lefthand = firstperson_lefthand;
    }

    public DisplayEntry getGui()
    {
        return gui;
    }

    public void setGui(DisplayEntry gui)
    {
        this.gui = gui;
    }

    public DisplayEntry getHead()
    {
        return head;
    }

    public void setHead(DisplayEntry head)
    {
        this.head = head;
    }

    public DisplayEntry getGround()
    {
        return ground;
    }

    public void setGround(DisplayEntry ground)
    {
        this.ground = ground;
    }

    public DisplayEntry getFixed()
    {
        return fixed;
    }

    /**
     * @param fixed refers to item frames
     */
    public void setFixed(DisplayEntry fixed)
    {
        this.fixed = fixed;
    }
}
