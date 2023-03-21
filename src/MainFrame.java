import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class MainFrame extends JFrame implements GLEventListener{

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;


    public MainFrame()
    {
        super("Java OpenGL");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(1000, 800);

        // This method will be explained later
        this.initializeJogl();

        this.setVisible(true);
    }


    private void initializeJogl()
    {
        // Obtaining a reference to the default GL profile
        GLProfile glProfile = GLProfile.getDefault();
        // Creating an object to manipulate OpenGL parameters.
        GLCapabilities capabilities = new GLCapabilities(glProfile);

        // Setting some OpenGL parameters.
        capabilities.setHardwareAccelerated(true);
        capabilities.setDoubleBuffered(true);

        // Creating an OpenGL display widget -- canvas.
        this.canvas = new GLCanvas();

        // Adding the canvas in the center of the frame.
        this.getContentPane().add(this.canvas);

        // Adding an OpenGL event listener to the canvas.
        this.canvas.addGLEventListener(this);
    }


    private GLCanvas canvas;


    public void init(GLAutoDrawable drawable) {

        GL2 gl = canvas.getGL().getGL2();


        // Activate the GL_LINE_SMOOTH state variable. Other options include
        // GL_POINT_SMOOTH and GL_POLYGON_SMOOTH.
        gl.glEnable(GL.GL_LINE_SMOOTH);

        // Activate the GL_BLEND state variable. Means activating blending.
        //gl.glEnable(GL.GL_BLEND);

        // Set the blend function. For antialiasing it is set to GL_SRC_ALPHA for the source
        // and GL_ONE_MINUS_SRC_ALPHA for the destination pixel.
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        // Control GL_LINE_SMOOTH_HINT by applying the GL_DONT_CARE behavior.
        // Other behaviours include GL_FASTEST or GL_NICEST.
        gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_DONT_CARE);
        // Uncomment the following two lines in case of polygon antialiasing
        //gl.glEnable(GL.GL_POLYGON_SMOOTH);
        //glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);

    }






    public void dispose(GLAutoDrawable drawable) {

    }


    public void display(GLAutoDrawable drawable) {
        // Retrieve a reference to a GL object. We need it because it contains all the useful OGL methods.
        GL2 gl = canvas.getGL().getGL2();

        // Do not render front-faced polygons.
        gl.glCullFace(GL.GL_FRONT);
        // Culling must be enabled in order to work.
        gl.glEnable(GL.GL_CULL_FACE);

        //gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);

        // Define vertices in clockwise order (back-faced)


        //gl.glBegin(GL2.GL_POLYGON);
        //corpul casei
        for(float i = 0f; i<= 1f; i=i+0.001f) {
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3d(0.255, 0.168, 0.100);
            gl.glVertex2d(-0.5, -1+i);
            gl.glVertex2d(0.5, -1+i);
            gl.glEnd();

        }
        //acoperis
        for(float i = 0f; i<= 0.6f; i=i+0.001f) {
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3d(0.788, 0.192, 0.192);
            gl.glVertex2d(-0.6+i, i);
            gl.glVertex2d(0.6-i, i);
            gl.glEnd();
        }

        //fereastra
        for(float i = 0f; i<= 0.3f; i=i+0.001f) {
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3d(0, 0, 0);
            gl.glVertex2d(-0.4, -0.4+i);
            gl.glVertex2d(-0.1, -0.4+i);
            gl.glEnd();
        }
        //lumina
        for(float i = 0f; i<= 0.22f; i=i+0.01f) {
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3d(1, 1, 0);
            gl.glVertex2d(-0.36, -0.36+i);
            gl.glVertex2d(-0.14, -0.36+i);
            gl.glEnd();
        }
        //usa
        for(float i = 0f; i<= 0.5f; i=i+0.001f) {
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3d(0.255, 0.200, 0.233);
            gl.glVertex2d(-0.15, -1+i);
            gl.glVertex2d(0.15, -1+i);
            gl.glEnd();
        }



    }





    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }
}