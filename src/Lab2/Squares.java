package Lab2;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

public class Squares extends JFrame implements GLEventListener {

    @Override
    public void init(GLAutoDrawable drawable) {
        // called once when the GLCanvas is created
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // define the vertices of the square
        float[] vertices = {
                -0.5f, -0.5f, // bottom-left
                0.5f, -0.5f, // bottom-right
                0.5f, 0.5f, // top-right
                -0.5f, 0.5f // top-left
        };

        // define the colors of the vertices
        float[] colors = {
                1.0f, 0.0f, 0.0f, // red
                0.0f, 1.0f, 0.0f, // green
                0.0f, 0.0f, 1.0f, // blue
                1.0f, 1.0f, 0.0f // yellow
        };

        // draw the square using GL_LINES
        /*
        gl.glBegin(GL.GL_LINES);
        for (int i = 0; i < 4; i++) {
            gl.glColor3f(colors[i*3], colors[i*3+1], colors[i*3+2]);
            int j = (i+1)%4;
            gl.glVertex2f(vertices[i*2], vertices[i*2+1]);
            gl.glVertex2f(vertices[j*2], vertices[j*2+1]);
        }
        gl.glEnd();
         */

        // draw the square using GL_LINES_STRIP
        /*
        gl.glBegin(GL.GL_LINE_STRIP);
        for (int i = 0; i < 5; i++) {
            gl.glColor3f(colors[(i%4)*3], colors[(i%4)*3+1], colors[(i%4)*3+2]);
            int j = i%4;
            gl.glVertex2f(vertices[j*2], vertices[j*2+1]);
        }
        gl.glEnd();
         */

        // draw the square using GL_LINE_LOOP
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int i = 0; i < 4; i++) {
            gl.glColor3f(colors[i*3], colors[i*3+1], colors[i*3+2]);
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
        canvas.addGLEventListener(new Squares());

        // create the JFrame and add the GLCanvas to it
        JFrame frame = new JFrame("Colored Square Example");
        frame.getContentPane().add(canvas);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}