//package Lab5;
//
//import Lab5.astro.PolarProjectionMap;
//import com.jogamp.opengl.GL2;
//import com.jogamp.opengl.GLAutoDrawable;
//
//public class MainFrame {
//
//// Holds a reference to the PolarProjectionMap object.
//private PolarProjectionMap ppm = null;
//// Used to identify the display list.
//private int ppm_list;
//
//public void init(GLAutoDrawable canvas)
//        {
//
//        // Initialize the object.
//        this.ppm = new PolarProjectionMap(21.53, 45.17);
//        // Set the separator for the line fields.
//        this.ppm.setFileSep(",");
//        // Read the file and compute the coordinates.
//        this.ppm.initializeConstellationLines("data/conlines.dat");
//        // Initialize here the rest of the elements from the remaining files using the corresponding methods.
//
//        // Create the display list.
//        this.ppm_list = gl.glGenLists(1);
//        gl.glNewList(this.ppm_list, GL.GL_COMPILE);
//        this.makePPM(gl);
//        gl.glEndList();
//        }
//
//public void display(GLAutoDrawable canvas)
//        {
//
//        gl.glCallList(this.ppm_list);
//        }
//
//// We use this method for creating the display list.
//private void makePPM(GL2 gl) {
//final ArrayList<PolarProjectionMap.ConstellationLine> clLines = this.ppm.getConLines();
//        // Add here the rest of the ArrayLists.
//
//        gl.glColor3f(0.0f, 1.0f, 0.0f);
//
//        gl.glBegin(GL2.GL_LINES);
//        for (PolarProjectionMap.ConstellationLine cl : clLines) {
//        if (cl.isVisible()) {
//        gl.glVertex2d(cl.getPosX1(), cl.getPosY1());
//        gl.glVertex2d(cl.getPosX2(), cl.getPosY2());
//        }
//        }
//        gl.glEnd();
//
//        // Add here the rest of the code for rendering constellation boundaries (use GL_LINES),
//        // names (use glutBitmapString), stars (use GL_POINTS) and cardinal points (use glutBitmapString).
//        }
//        }
