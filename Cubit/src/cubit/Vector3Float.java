package cubit;

/**
 * Vector3Float is an object that holds our camera's position
 * in 3D space. 
 * @author Binary Shredders (c) 2015
 */
public class Vector3Float
{
    /**
     * x, y, and z represents the 3-d coordinate 
     * of our camera
     */
    public float x, y, z;
    
    /**
     * Constructs a Vector3Float, which will hold the camera's
     * position in 3D space.
     * @param x-coordinate of the camera's position
     * @param y-coordinate of the camera's position
     * @param z-coordinate of the camera's position
     */
    public Vector3Float(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}