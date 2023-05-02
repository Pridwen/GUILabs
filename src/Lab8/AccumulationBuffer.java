package Lab8;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;

public class AccumulationBuffer extends JFrame implements GLEventListener {

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1.0, 1.0, -1.0, 1.0, -1.0, 1.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_ACCUM_BUFFER_BIT);

        int numVertices = 5;
        float radius = 0.8f;
        float centerX = 0.0f;
        float centerY = 0.0f;

        float[] vertices = new float[numVertices * 3];

        // Calculate the vertex positions
        for (int i = 0; i < numVertices; i++) {
            float angle = (float) i / numVertices * 360.0f;
            float x = centerX + (float) Math.cos(Math.toRadians(angle)) * radius;
            float y = centerY + (float) Math.sin(Math.toRadians(angle)) * radius;
            vertices[i * 3] = x;
            vertices[i * 3 + 1] = y;
            vertices[i * 3 + 2] = 0.0f;
        }

        // Draw the pentagon to the accumulation buffer multiple times
        for (int i = 0; i < 10; i++) {
            float[] color = { i / 10.0f, 0.0f, 0.0f };
            gl.glColor3fv(color, 0);

            gl.glBegin(GL2.GL_TRIANGLE_FAN);
            for (int j = 0; j < numVertices; j++) {
                gl.glVertex3fv(vertices, j * 3);
            }
            gl.glEnd();

            gl.glAccum(GL2.GL_ACCUM, 0.1f);
        }

        // Copy the accumulation buffer to the color buffer
        gl.glAccum(GL2.GL_RETURN, 1.0f);
    }


    @Override
    public void dispose(GLAutoDrawable drawable) {
        // called when the GLCanvas is destroyed
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Accumulation Buffer");
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new ColorBuffer());
        frame.getContentPane().add(canvas);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
}

