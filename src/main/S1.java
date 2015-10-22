package main;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class S1 extends Frame implements GLEventListener {

    private static final int WIDTH  = 640;
    private static final int HEIGHT = 480;
    // private static final float NEAR_CLIP=0.1f;
    // private static final float FAR_CLIP=100.0f;

    private GLCanvas    canvas;
    private FPSAnimator animator;
    private double      theta    = 0;
    private double      sinValue = 0;
    private double      cosValue = 0;

    public static void main(String[] args) {
        S1 frame = new S1();
        frame.setVisible(true);
    }

    public S1() {
        super("S1");
        this.setSize(WIDTH, HEIGHT);

        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        this.add(canvas, "Center");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        canvas.addGLEventListener(this);

        Optional<Integer> a = Optional.of(6);
        System.out.println(a.get());

        animator = new FPSAnimator(canvas, 60);

        animator.start();
    }

    /* METHODS DEFINED BY GLEventListener */

    /* Called by drawable to initiate drawing */
    public void display(GLAutoDrawable drawable) {
        update();
        render(drawable);
    }

    /* Called to indicate the drawing surface has been moved and/or resized */
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
        int height) {
    }

    /* OpenGL initialisation */
    public void init(GLAutoDrawable drawable) {
        // GL gl = drawable.getGL();
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 1); // black
    }

    public void dispose(GLAutoDrawable drawable) {
    }

    private void update() {
        theta += 0.01;
        sinValue = Math.sin(theta); // angle in radians
        cosValue = Math.cos(theta); // angle in radians
    }

    private void render(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // draw a triangle using full window size (-1,-1) to (1,1)
        gl.glBegin(GL.GL_TRIANGLES);
        gl.glColor3f(1, 0, 0); // red
                               // (red, green, blue),
                               // each in range 0.0..1.0
        gl.glVertex2d(-cosValue, -cosValue); // d=double-precision: x, y values
                                             // openGL treats this as (-c,-c,0)
                                             // (i.e. a default z value of 0 is
                                             // assumed)
        gl.glColor3f(0, 1, 0); // green
        gl.glVertex2d(0, cosValue);
        gl.glColor3f(0, 0, 1); // blue
        gl.glVertex2d(sinValue, -sinValue);
        gl.glEnd();
    }
}
