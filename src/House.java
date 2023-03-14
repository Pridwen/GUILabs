import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

public class House extends JFrame implements GLEventListener {

    @Override
    public void init(GLAutoDrawable drawable) {
        // called once when the GLCanvas is created
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // draw the square for the house in pink
        gl.glColor3f(1.0f, 0.0f, 0.0f); // red color
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(-0.5f, -0.5f);
        gl.glVertex2f(0.5f, -0.5f);
        gl.glVertex2f(0.5f, -0.5f);
        gl.glVertex2f(0.5f, 0.5f);
        gl.glVertex2f(0.5f, 0.5f);
        gl.glVertex2f(-0.5f, 0.5f);
        gl.glVertex2f(-0.5f, 0.5f);
        gl.glVertex2f(-0.5f, -0.5f);
        gl.glEnd();

        // draw the triangle for the roof in white
        gl.glColor3f(0.0f, 1.0f, 0.0f); // white color
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2f(-0.5f, 0.5f);
        gl.glVertex2f(0.0f, 1.0f);
        gl.glVertex2f(0.0f, 1.0f);
        gl.glVertex2f(0.5f, 0.5f);
        gl.glEnd();

        // draw the yellow circle on top of the house
        gl.glColor3f(1.0f, 1.0f, 0.0f); // yellow color
        gl.glBegin(GL.GL_LINE_LOOP);
        int num_segments = 50;
        float radius = 0.2f;
        for (int i = 0; i < num_segments; i++) {
            float theta = (float)i/(float)num_segments * 2.0f * (float)Math.PI;
            float x = 0.7f + radius * (float)Math.cos(theta); // x-coordinate
            float y = 0.7f + radius * (float)Math.sin(theta); // y-coordinate
            gl.glVertex2f(x, y);
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
        canvas.addGLEventListener(new House());

        // create the JFrame and add the GLCanvas to it
        JFrame frame = new JFrame("House Example");
        frame.getContentPane().add(canvas);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}