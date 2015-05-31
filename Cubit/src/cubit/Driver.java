
package cubit;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;


public class Driver 
{
    private FPCameraController fp;
    private DisplayMode displayMode;
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;
        
    public void start()
    {
        try
        {
            createWindow();
            initGL();
            fp = new FPCameraController(0f, 0f, 0f);
            fp.gameLoop(); // render method
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void createWindow() throws Exception
    {
        Display.setFullscreen(false);
        DisplayMode[] d = Display.getAvailableDisplayModes();
        for (int i = 0; i < d.length; ++i)
        {
            if (d[i].getWidth() == 640 && d[i].getHeight() == 480
                    && d[i].getBitsPerPixel() == 32)
            {
                displayMode = d[i];
                break;
            }
        }
        Display.setDisplayMode(displayMode);
        Display.setTitle("+ c u b i t +");
        Display.create();
    }
    
    public void initGL()
    {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100.0f, (float)displayMode.getWidth()/(float)displayMode.getHeight(), 0.1f, 300.0f);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        
        /* 
         * The following lines of code
         * are written to accomodate
         * for new Block and Chunk objs.
         */
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
        
        /*
         * The following lines of code
         * are written for texture mapping.
         */
        glEnable(GL_TEXTURE_2D);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        
        //following lines for lighting
        initLightArrays();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition); //sets light position
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight); //sets specular light
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight); //sets diffuse light
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight); //sets ambient light
        
        glEnable(GL_LIGHTING); //enables our light
        glEnable(GL_LIGHT0); //enables light0
    }
    
    //this method initializes lighting
    private void initLightArrays() {
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();
    }
        
    public static void main(String[] args)
    {
        Driver driver = new Driver();
        driver.start();
    }
}
