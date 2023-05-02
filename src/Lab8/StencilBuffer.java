package Lab8;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;
import java.nio.FloatBuffer;

public class StencilBuffer extends JFrame implements GLEventListener {

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
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_STENCIL_BUFFER_BIT);

        // Enable the stencil buffer
        gl.glEnable(GL2.GL_STENCIL_TEST);
        gl.glStencilFunc(GL2.GL_ALWAYS, 1, 0xFF);
        gl.glStencilOp(GL2.GL_KEEP, GL2.GL_KEEP, GL2.GL_REPLACE);

        // Draw the stencil pattern
        gl.glColorMask(false, false, false, false);
        gl.glDepthMask(false);
        gl.glStencilMask(0xFF);
        gl.glClear(GL2.GL_STENCIL_BUFFER_BIT);

        float radius = 0.8f;
        float centerX = 0.0f;
        float centerY = 0.0f;

        int numVertices = 5;
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

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        for (int i = 0; i < numVertices; i++) {
            gl.glVertex3fv(vertices, i * 3);
        }
        gl.glEnd();

        // Enable color and depth masking
        gl.glColorMask(true, true, true, true);
        gl.glDepthMask(true);

        // Draw the color pattern
        gl.glStencilFunc(GL2.GL_EQUAL, 1, 0xFF);
        gl.glStencilMask(0x00);

        float[] colors = {
                1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f,
                1.0f, 0.0f, 1.0f
        };

        gl.glEnableVertexAttribArray(0);
        gl.glEnableVertexAttribArray(1);

        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertices);
        gl.glVertexAttribPointer(0, 3, GL2.GL_FLOAT, false, 0, vertexBuffer);

        FloatBuffer colorBuffer = Buffers.newDirectFloatBuffer(colors);
        gl.glVertexAttribPointer(1, 3, GL2.GL_FLOAT, false, 0, colorBuffer);

        gl.glDrawArrays(GL2.GL_TRIANGLE_FAN, 0, numVertices);

        gl.glDisableVertexAttribArray(0);
        gl.glDisableVertexAttribArray(1);

        // Disable the stencil buffer
        gl.glDisable(GL2.GL_STENCIL_TEST);
    }


    @Override
    public void dispose(GLAutoDrawable drawable) {
        // called when the GLCanvas is destroyed
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Stencil Buffer");
        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new ColorBuffer());
        frame.getContentPane().add(canvas);
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
}
