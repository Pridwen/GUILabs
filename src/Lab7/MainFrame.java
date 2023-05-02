package Lab7;


import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class MainFrame extends GLCanvas implements GLEventListener, KeyListener {
    private static final float SUN_RADIUS = 12f;
    private GLU glu;
    private float MoonAngle = 0;
    private float EarthAngle = 0;
    private float SystemAngle = 0;
    private float SunAngle = 0;
    private Texture earth;
    private Texture clouds;
    private Texture moon;
    private Texture sun;
    float cameraAzimuth = 0.0f, cameraSpeed = 0.0f, cameraElevation = 0.0f;
    float cameraX = 0.0f, cameraY = 0.0f, cameraZ = -20.0f;
    float cameraUpx = 0.0f, cameraUpy = 1.0f, cameraUpz = 0.0f;
    public MainFrame(int width, int height, GLCapabilities capabilities) {
        super(capabilities);
        setSize(width, height);
        addGLEventListener(this);
    }
    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        glu = new GLU();
        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        this.addKeyListener(this);
        FPSAnimator animator = new FPSAnimator(this, 60);
        animator.start();

        String EarthTex = "src/Lab7/Textures/EarthTex.png";
        earth = getObjectTexture(gl, EarthTex);
        String CloudTex = "src/Lab7/Textures/CloudTex.png";
        clouds = getObjectTexture(gl, CloudTex);
        String MoonTex = "src/Lab7/Textures/MoonTex.png";
        moon = getObjectTexture(gl, MoonTex);

        String SunTex = "src/Lab7/Textures/SunTex.png";
        sun = getObjectTexture(gl, SunTex);
        //this.sun = new Sun(gl, glu, getObjectTexture(gl, SunTex));
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        final GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        float widthHeightRatio = (float) getWidth() / (float) getHeight();
        glu.gluPerspective(45, widthHeightRatio, 1, 1000);
        glu.gluLookAt(0, 0, 200, 0, 0, 0, 0, 1, 0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        aimCamera(gl, glu);
        moveCamera();
        setLights(gl);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        drawSun(gl, glu, sun);
        gl.glPushMatrix();
        SystemAngle = (SystemAngle + 0.4f) % 360f;

        final float distance = SUN_RADIUS + 30f;
        final float x = (float) Math.sin(Math.toRadians(SystemAngle)) * distance;
        final float y = (float) Math.cos(Math.toRadians(SystemAngle)) * distance;
        final float z = 0;
        gl.glTranslatef(x, y, z);

        drawEarth(gl);
        drawMoon(gl);
        gl.glPopMatrix();
    }

    private void drawSun(GL2 gl, GLU glu, Texture sun) {
        final float radius = 10f;										// size of sun
        final int slices = 32;											// divide sun into slices/stack parts, more parts make the sun look better
        final int stacks = 32;
        sun.enable(gl);
        sun.bind(gl);
        gl.glPushName(6);											// push id 6 into stack
        SunAngle = (SunAngle + 0.7f) % 360f;							// constant update of angle
        gl.glPushMatrix();												// push current matrix info in stack
        gl.glRotatef(SunAngle, 0.8f, 0.1f, 0);					// make rotation
        GLUquadric sunQ = glu.gluNewQuadric();							// create sun object
        glu.gluQuadricTexture(sunQ, true);							// get coords of sun object
        glu.gluQuadricDrawStyle(sunQ, GLU.GLU_FILL);					// fill the sun object
        glu.gluSphere(sunQ, radius, slices, stacks);					// now make the sun into a sphere
        glu.gluDeleteQuadric(sunQ);										// free memory
        gl.glPopMatrix();
        gl.glPopName();
    }


    private void drawEarth(GL2 gl) {
        float[] rgba = { 1f, 1f, 1f };
        final float radius = 6.378f;
        final int slices = 16;
        final int stacks = 16;
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
        gl.glPushName(4);

        EarthAngle = (EarthAngle + 0.1f) % 360f;

        clouds.enable(gl);
        clouds.bind(gl);
        gl.glPushMatrix();
        gl.glRotatef(EarthAngle, 0.2f, 0.1f, 0);

        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_COLOR, GL.GL_DST_ALPHA);
        GLUquadric clouds = glu.gluNewQuadric();
        glu.gluQuadricOrientation(clouds, GLU.GLU_OUTSIDE);
        glu.gluQuadricTexture(clouds, true);
        glu.gluSphere(clouds, 7, slices, stacks);

        earth.enable(gl);
        earth.bind(gl);
        gl.glDisable(GL.GL_BLEND);

        GLUquadric earth = glu.gluNewQuadric();
        glu.gluQuadricTexture(earth, true);
        glu.gluQuadricDrawStyle(earth, GLU.GLU_FILL);
        glu.gluSphere(earth, radius, slices, stacks);
        gl.glPopName();

        glu.gluDeleteQuadric(earth);
        glu.gluDeleteQuadric(clouds);
        gl.glPopMatrix();
    }

    private void drawMoon(GL2 gl) {
        gl.glPushMatrix();
        final float distance = 12.000f;
        final float x = (float) Math.sin(Math.toRadians(MoonAngle)) * distance;
        final int y = (int) ((float) Math.cos(Math.toRadians(MoonAngle)) * distance);
        final float z = 0;
        final float radius = 3.378f;
        final int slices = 16;
        final int stacks = 16;

        moon.enable(gl);
        moon.bind(gl);
        gl.glPushName(5);
        MoonAngle = (MoonAngle + 1f) % 360f;
        gl.glTranslatef(x, y, z);
        gl.glRotatef(MoonAngle, 0, 0, -1);
        gl.glRotatef(45f, 0, 1, 0);

        GLUquadric moon = glu.gluNewQuadric();
        glu.gluQuadricTexture(moon, true);
        glu.gluQuadricDrawStyle(moon, GLU.GLU_FILL);
        glu.gluSphere(moon, radius, slices, stacks);

        gl.glPopMatrix();
        gl.glPopName();
    }

    private Texture getObjectTexture(GL2 gl, String fileName) {
        InputStream stream;
        Texture tex;
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        try {
            stream = new FileInputStream(new File(fileName));
            TextureData data = TextureIO.newTextureData(gl.getGLProfile(), stream, false, extension);
            tex = TextureIO.newTexture(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tex;
    }
    private void setLights(GL2 gl) {
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = { 0, 0, 0, SHINE_ALL_DIRECTIONS };
        float[] lightColorAmbient = { 0.5f, 0.5f, 0.5f, 1f };
        float[] lightColorSpecular = { 0.8f, 0.8f, 0.8f, 1f };
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHTING);

    }
    // ciordeala de la Lab7 ¯\_(ツ)_/¯
    // am schimbat doar keybinds
    public void moveCamera() {
        float[] tmp = polarToCartesian(cameraAzimuth, cameraSpeed, cameraElevation);

        cameraX += tmp[0];
        cameraY += tmp[1];
        cameraZ += tmp[2];
    }
    public void aimCamera(GL2 gl, GLU glu) {
        gl.glLoadIdentity();
        float[] tmp = polarToCartesian(cameraAzimuth, 100.0f, cameraElevation);
        float[] camUp = polarToCartesian(cameraAzimuth, 100.0f, cameraElevation + 90);
        cameraUpx = camUp[0];
        cameraUpy = camUp[1];
        cameraUpz = camUp[2];
        glu.gluLookAt(cameraX, cameraY, cameraZ, cameraX + tmp[0],
                cameraY + tmp[1], cameraZ + tmp[2], cameraUpx, cameraUpy, cameraUpz);
    }
    private float[] polarToCartesian(float azimuth, float length, float altitude) {
        float[] result = new float[3];
        float x, y, z;
        float theta = (float) Math.toRadians(90 - azimuth);
        float tantheta = (float) Math.tan(theta);
        float radian_alt = (float) Math.toRadians(altitude);
        float cospsi = (float) Math.cos(radian_alt);
        x = (float) Math.sqrt((length * length) / (tantheta * tantheta + 1));
        z = tantheta * x;
        x = -x;
        if ((azimuth >= 180.0 && azimuth <= 360.0) || azimuth == 0.0f) {
            x = -x;
            z = -z;
        }
        // Calculate y, and adjust x and z
        y = (float) (Math.sqrt(z * z + x * x) * Math.sin(radian_alt));
        if (length < 0) {
            x = -x;
            z = -z;
            y = -y;
        }
        x = x * cospsi;
        z = z * cospsi;
        result[0] = x;
        result[1] = y;
        result[2] = z;
        return result;
    }
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_W) {
            cameraElevation -= 2;
        }
        if (event.getKeyCode() == KeyEvent.VK_S) {
            cameraElevation += 2;
        }
        if (event.getKeyCode() == KeyEvent.VK_D) {
            cameraAzimuth -= 2;
        }
        if (event.getKeyCode() == KeyEvent.VK_A) {
            cameraAzimuth += 2;
        }
        if (event.getKeyCode() == KeyEvent.VK_UP) {
            cameraSpeed += 0.05;
        }
        if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            cameraSpeed -= 0.05;
        }
        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            cameraSpeed = 0;
        }
        if (event.getKeyCode() < 250)
            keys[event.getKeyCode()] = true;
        if (cameraAzimuth > 359)
            cameraAzimuth = 1;

        if (cameraAzimuth < 1)
            cameraAzimuth = 359;
    }
    private final boolean[] keys = new boolean[250];
    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }
    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }
}

