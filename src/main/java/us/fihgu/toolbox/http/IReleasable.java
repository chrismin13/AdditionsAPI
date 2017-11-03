package us.fihgu.toolbox.http;

/**
 * A resource that can be released, reconstructed.<br>
 * An implementation should keep a track of all access points that uses this resource<br>
 * When all access points are released, it should release resource used.
 * @author fihgu
 */
public interface IReleasable
{
	/**
	 * Open an new access point, register and return it. <br>
	 * implementation should check and reconstruct the resource if needed. <br>
	 */
    AccessPoint aquireAccess();
	
	/**
	 * release a registered accessPoint. <br>
	 * implementation should check if there is any accessPoint left, and release the resource accordingly.
	 */
    void removeAccessPoint(AccessPoint accessPoint);
}
