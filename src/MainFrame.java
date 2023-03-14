import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements GLEventListener
{

    public MainFrame()
    {
        super("Java OpenGL");

        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(800, 600);

        // This method will be explained later
        // this.initializeJogl();

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
        Animator animator = new Animator();

        animator.add(this.canvas);

        animator.start();
    }

    private GLCanvas canvas;
    public void init(GLAutoDrawable canvas)
    {
        return;
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }

    public void display(GLAutoDrawable canvas) {
        GL2 gl = canvas.getGL().getGL2();

        // Each time the scene is redrawn we clear the color buffers which is perceived by the user as clearing the scene.

        // Set the color buffer to be filled with the color black when cleared.
        // It can be defined in the init function (method) also.

        // Clear the color buffer.
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);


        gl.glBegin(GL2.GL_POINTS);
        // Set the vertex color to Red.
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex2f(0.2f, 0.2f);
        // Set the vertex color to Green.
        gl.glColor3f(0.0f, 1.0f, 0.0f);
        gl.glVertex2f(0.4f, 0.2f);
        // Set the vertex color to Blue.
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex2f(0.2f, 0.4f);
        // Set the vertex color to White.
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glVertex2f(0.4f, 0.4f);
        gl.glEnd();

    }

    public void reshape(GLAutoDrawable canvas, int left, int top, int width, int height)
    {
        return;
    }

    public void displayChanged(GLAutoDrawable canvas, boolean modeChanged, boolean deviceChanged)
    {
        return;
    }
}