package cubit;

/**
 * Imports for the FPCameraController class.
 */
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;
import java.nio.FloatBuffer;

/**
 * FPCameraController is our first person camera controller,
 * allowing us to see things in first person perspective.
 * Move around the world using traditional WASD keys.
 * Look around the world using your mouse. 
 * @author Binary Shredders (c) 2015
 */
public class FPCameraController
{
    /**
     * These two Vector3f objects are 3d vectors
     * that store the camera's position.
     */
    private Vector3f position = null;    
    // ????? what is lPosition for?
    private Vector3f lPosition = null;
    /* The rotation around the Y-axis of the camera */
    private float yaw = 0.0f;    
    /* The rotation around the X-axis of the camera */
    private float pitch = 0.0f;    
    /* ...Not quite sure what you're used for yet... */
    private Vector3Float me;
    
    private Chunk earth = new Chunk(0,0,0);
    
    /**
     * Constructs this FPCameraController object by instantiating
     * the position Vector3fs to the parameters x, y, and z.
     * @param x-coordinate of the camera's position
     * @param y-coordinate of the camera's position
     * @param z-coordinate of the camera's position
     */
    public FPCameraController(float x,float y, float z)
    {
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x, y, z);
        
        lPosition.x = 0f;
        lPosition.y = 15f; /// What does this change?
        lPosition.z = 0f;
    }
    
    /**
     * Increment the camera's current yaw rotation. 
     * Yaw is the camera's rotation around the y-axis.
     * @param amount to increase the rotation about the y-axis. 
     */
    public void yaw(float amount)
    {
        yaw += amount;
    }
    
    /**
     * Increment the camera's current pitch rotation. 
     * Yaw is the camera's rotation around the x-axis.
     * @param amount to increase the rotation about the x-axis. 
     */
    public void pitch(float amount)
    {
        pitch -= amount;        
    }
    
    /**
     * Move the camera forward relative to it's current rotation
     * (yaw).
     * @param distance to walk forward relative to yaw
     */
    public void walkForward(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
        
        //for lighting
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x -= xOffset).put(lPosition.y).put(
                lPosition.z += zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /**
     * Move the camera backwards relative to it's current rotation
     * (yaw).
     * @param distance to walk backward relative to yaw
     */
    public void walkBackwards(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
        
        //for lighting
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x -= xOffset).put(lPosition.y).put(
                lPosition.z += zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /**
     * Moves the character to the left. Similar to side-stepping
     * to the left.
     * @param distance to side-step to the left
     */
    public void strafeLeft(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw-90));
        position.x -= xOffset;
        position.z += zOffset;
        
        //for lighting
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x -= xOffset).put(lPosition.y).put(
                lPosition.z += zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /**
     * Moves the character to the left. Similar to side-stepping
     * to the left.
     * @param distance to side-step to the left
     */
    public void strafeRight(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw+90));
        position.x -= xOffset;
        position.z += zOffset;
        
        //for lighting
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x -= xOffset).put(lPosition.y).put(
                lPosition.z += zOffset).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    
    /**
     * Moves the camera up infinitely.
     * @param distance to move up.
     */
    public void moveUp(float distance)
    {
        position.y -= distance;
    }
    
    /**
     * Moves the camera down infinitely.
     * @param distance to move down.
     */
    public void moveDown(float distance)
    {
        position.y += distance;
    }
    
    /**
     * Translate and rotate the matrix so that it looks through
     * the camera. This creates a viewing matrix derived from thee
     * pitch, yaw, and position of the camera.
     */
    public void lookThrough()
    {
        // Rotates the pitch around the X-axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        // Rotates the yaw around the Y-axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        // Translates to the position vector's location
        glTranslatef(position.x, position.y, position.z);
        
        //for lighting
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x).put(lPosition.y).put(lPosition.z).put(
            1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
                
    }
    
    /**
     * 
     */
    public void gameLoop()
    {
        FPCameraController camera = new FPCameraController(0,0,0);
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f;
        
        // When the last frame was
        float lastTime = 0.0f;
        long time = 0;
        float mouseSensitivity = 0.09f;
        float movementSpeed = 0.2f;
        
        // Hide the mouse
        Mouse.setGrabbed(true);
        
        // Keep looping until the display window is closed or ESC is pressed
        while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            time = Sys.getTime();
            lastTime = time;
            
            // Distance in the mouse movement from the last getDX() call
            dx = Mouse.getDX();
            // Distance in the mouse movement from the last getDYs() call
            dy = Mouse.getDY();
            
            // Controls the camera yaw from the mouse's x-movement
            camera.yaw(dx * mouseSensitivity);
            // Controls the camera pitch from the mouse's y-movement
            camera.pitch(dy * mouseSensitivity);
            
            // W = move forward
            if (Keyboard.isKeyDown(Keyboard.KEY_W))
            {
                camera.walkForward(movementSpeed);
            }
            // S = move backward
            if (Keyboard.isKeyDown(Keyboard.KEY_S))
            {
                camera.walkBackwards(movementSpeed);
            }
            // A = strafe left
            if (Keyboard.isKeyDown(Keyboard.KEY_A))
            {
                camera.strafeLeft(movementSpeed);
            }
            // D = strafe right
            if (Keyboard.isKeyDown(Keyboard.KEY_D))
            {
                camera.strafeRight(movementSpeed);
            }
            // SPACE = move up
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
            {
                camera.moveUp(movementSpeed);
            }
            // LSHIFT = move down
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) 
            {
                camera.moveDown(movementSpeed);
            }
            
            // Set the modelview matrix back to the identity
            glLoadIdentity();
            
            // Look through the camera before you draw anything
            camera.lookThrough();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            // Draw/render your scene
            earth.render();
            
            // Draw the render buffer to the screen
            Display.update();
            Display.sync(60);
        }
        Display.destroy();
    }
    
    private void render()
    {
        try
        {
            glBegin(GL_QUADS);
                //front
                glColor3f(0.0f, 0.0f, 1.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
                //back
                glColor3f(0.0f, 1.0f, 0.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                //top
                glColor3f(0.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                //bottom
                glColor3f(1.0f, 0.0f, 0.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
                //left
                glColor3f(1.0f, 0.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                //right
                glColor3f(1.0f, 1.0f, 0.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
            glEnd();
        }
        catch(Exception e)
        {
            
        }
    }
    
    
}