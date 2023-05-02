package Lab8;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;
import java.nio.FloatBuffer;

public class ColorBuffer extends JFrame implements GLEventListener {

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
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

        float radius = 0.8f;
        float centerX = 0.0f;
        float centerY = 0.0f;

        int numVertices = 5;
        float[] vertices = new float[numVertices * 3];
        float[] colors = new float[numVertices * 3];

        // Calculate the vertex positions and colors
        for (int i = 0; i < numVertices; i++) {
            float angle = (float) i / numVertices * 360.0f;
            float x = centerX + (float) Math.cos(Math.toRadians(angle)) * radius;
            float y = centerY + (float) Math.sin(Math.toRadians(angle)) * radius;
            vertices[i * 3] = x;
            vertices[i * 3 + 1] = y;
            vertices[i * 3 + 2] = 0.0f;
            colors[i * 3] = (float) i / numVertices;
            colors[i * 3 + 1] = (float) (numVertices - i) / numVertices;
            colors[i * 3 + 2] = 0.0f;
        }

        gl.glEnableVertexAttribArray(0);
        gl.glEnableVertexAttribArray(1);

        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertices);
        gl.glVertexAttribPointer(0, 3, GL2.GL_FLOAT, false, 0, vertexBuffer);

        FloatBuffer colorBuffer = Buffers.newDirectFloatBuffer(colors);
        gl.glVertexAttribPointer(1, 3, GL2.GL_FLOAT, false, 0, colorBuffer);

        gl.glDrawArrays(GL2.GL_TRIANGLE_FAN, 0, numVertices);

        gl.glDisableVertexAttribArray(0);
        gl.glDisableVertexAttribArray(1);
    }


    @Override
    public void dispose(GLAutoDrawable drawable) {
        // called when the GLCanvas is destroyed
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Color Buffer");
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new ColorBuffer());
        frame.getContentPane().add(canvas);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
}