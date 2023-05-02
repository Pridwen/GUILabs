import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;

public class MainFrame implements GLEventListener {
    private float angleC1 = 0.0f;
    private float angleC2 = 0.0f;
    public static void main(String[] args) {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        JFrame frame = new JFrame("Three Spheres");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(canvas);
        frame.setVisible(true);

        canvas.addGLEventListener(new MainFrame());

        FPSAnimator animator = new FPSAnimator(canvas, 60);
        animator.start();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

        // Draw the first sphere (C1)
        gl.glColor3f(1, 0, 0); // Red
        gl.glPushMatrix();
        gl.glRotatef(angleC1, 0, 1, 0); // Rotate around y-axis
        glut.glutSolidSphere(0.5, 32, 32); // Draw sphere
        gl.glPopMatrix();

        // Draw the second sphere (C2)
        gl.glColor3f(0, 1, 0); // Green
        gl.glPushMatrix();
        gl.glRotatef(angleC2, 0, 1, 0); // Rotate around y-axis
        gl.glTranslatef(1, 0, 0); // Move right relative to C1
        glut.glutSolidSphere(0.5, 32, 32); // Draw sphere
        gl.glPopMatrix();

        // Draw the third sphere (C3)
        gl.glColor3f(0, 0, 1); // Blue
        gl.glPushMatrix();
        gl.glRotatef(angleC2, 0, 1, 0); // Rotate around y-axis (same as C2)
        gl.glTranslatef(0.5f, 0, 0); // Move right relative to C2
        gl.glRotatef(angleC1, 0, 1, 0); // Rotate around y-axis (same as C1)
        glut.glutSolidSphere(0.2f, 32, 32); // Draw sphere
        gl.glPopMatrix();

        angleC1 += 1.0f; // Increment rotation angle for C1
        angleC2 += 2.0f; // Increment rotation angle for C2
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 0);
        glut = new com.jogamp.opengl.util.gl2.GLUT();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        float aspect = (float) width / height;
        gl.glOrtho(-aspect, aspect, -1, 1, -1, 1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private com.jogamp.opengl.util.gl2.GLUT glut;
}