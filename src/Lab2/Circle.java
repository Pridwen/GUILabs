package Lab2;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

public class Circle extends JFrame implements GLEventListener {

    private static final int NUM_SEGMENTS = 50; // number of line segments to use
    private static final float RADIUS = 0.5f; // radius of the circle

    @Override
    public void init(GLAutoDrawable drawable) {
        // called once when the GLCanvas is created
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // calculate the vertices of the circle
        float[] vertices = new float[NUM_SEGMENTS*2];
        for (int i = 0; i < NUM_SEGMENTS; i++) {
            float theta = (float)i/(float)NUM_SEGMENTS * 2.0f * (float)Math.PI;
            vertices[i*2] = RADIUS * (float)Math.cos(theta); // x-coordinate
            vertices[i*2+1] = RADIUS * (float)Math.sin(theta); // y-coordinate
        }

        // draw the circle using GL_LINE_LOOP
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int i = 0; i < NUM_SEGMENTS; i++) {
            gl.glVertex2f(vertices[i*2], vertices[i*2+1]);
        }
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // called when the size of the GLCanvas changes
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // called when the GLCanvas is destroyed
    }

    public static void main(String[] args) {
        // create the GLCanvas and add this as a GLEventListener
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new Circle());

        // create the JFrame and add the GLCanvas to it
        JFrame frame = new JFrame("Lab2.Circle Example");
        frame.getContentPane().add(canvas);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
