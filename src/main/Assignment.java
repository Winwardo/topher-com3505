package main;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

public class Assignment extends Frame
    implements GLEventListener, ActionListener {

    private static final int   WIDTH     = 640;
    private static final int   HEIGHT    = 480;
    private static final int   SCALING   = 2;
    private static final float NEAR_CLIP = 0.1f;
    private static final float FAR_CLIP  = 100.0f;

    private Scene    scene;
    private GLCanvas canvas;

    public static void main(String[] args) {
        Assignment t1 = new Assignment();
        t1.setVisible(true);
    }

    public Assignment() {
        super("T1");
        setSize(WIDTH * SCALING, HEIGHT * SCALING);

        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        canvas = new GLCanvas(caps);
        add(canvas, "Center");

        setupExitEvents();
        setupUI();

        FPSAnimator animator = new FPSAnimator(canvas, 60);

        animator.start();
    }

    private void setupUI() {
        addMenuBar();
        addRotateButton();
    }

    private void addMenuBar() {
        MenuBar menuBar = new MenuBar();
        this.setMenuBar(menuBar);
        Menu fileMenu = new Menu("File");
        MenuItem quitItem = new MenuItem("Quit");
        quitItem.addActionListener(this);
        fileMenu.add(quitItem);
        menuBar.add(fileMenu);
    }

    private void addRotateButton() {
        Panel p = new Panel();
        Button rotate = new Button("Rotate");
        rotate.addActionListener(this);
        p.add(rotate);
        this.add(p, "South");
    }

    private void setupExitEvents() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        canvas.addGLEventListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("rotate")) {
            scene.rotate();
        } else
            if (e.getActionCommand().equalsIgnoreCase("quit")) System.exit(0);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        scene.update();
        scene.render();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 1); // black
        gl.glEnable(GL.GL_DEPTH_TEST);
        scene = new Scene(gl);
    }

    /* Called to indicate the drawing surface has been moved and/or resized */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
        int height) {
        GL2 gl = drawable.getGL().getGL2();

        float fAspect = (float) width / height;
        float fovy = 60.0f;

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        float top = (float) Math.tan(Math.toRadians(fovy * 0.5)) * NEAR_CLIP;
        float bottom = -top;
        float left = fAspect * bottom;
        float right = fAspect * top;

        gl.glFrustum(left, right, bottom, top, NEAR_CLIP, FAR_CLIP);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

}
