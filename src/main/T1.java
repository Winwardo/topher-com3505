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
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

public class T1 extends Frame implements GLEventListener, ActionListener {

	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;
	private static final float NEAR_CLIP = 0.1f;
	private static final float FAR_CLIP = 100.0f;

	private Scene scene;
	private GLCanvas canvas;

	public static void main(String[] args) {
		T1 t1 = new T1();
		t1.setVisible(true);
	}

	public T1() {
		super("T1");
		setSize(WIDTH, HEIGHT);

		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		canvas = new GLCanvas(caps);
		add(canvas, "Center");

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		canvas.addGLEventListener(this);

		MenuBar menuBar = new MenuBar();
		this.setMenuBar(menuBar);
		Menu fileMenu = new Menu("File");
		MenuItem quitItem = new MenuItem("Quit");
		quitItem.addActionListener(this);
		fileMenu.add(quitItem);
		menuBar.add(fileMenu);

		Panel p = new Panel();
		Button rotate = new Button("Rotate");
		rotate.addActionListener(this);
		p.add(rotate);
		this.add(p, "South");

		FPSAnimator animator = new FPSAnimator(canvas, 60);

		animator.start();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equalsIgnoreCase("rotate")) {
			scene.rotate();
			// canvas.repaint(); // if animator is not used
		} else if (e.getActionCommand().equalsIgnoreCase("quit"))
			System.exit(0);
	}

	/*
	 * METHODS DEFINED BY GLEventListener
	 */

	/* draw */
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		scene.update();
		scene.render(gl);
	}

	public void dispose(GLAutoDrawable drawable) {
	}

	/* initialisation */
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0, 0, 0, 1); // black
		gl.glEnable(GL.GL_DEPTH_TEST);
		scene = new Scene();
	}

	/* Called to indicate the drawing surface has been moved and/or resized */
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
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

class Scene {

	private GLU glu = new GLU();
	private GLUT glut = new GLUT();

	private static final double INC_ROTATE = 5;
	private double rotate = 0;

	public Scene() {
	}

	public void rotate() {
		rotate += INC_ROTATE;
	}

	public void update() {
		// do nothing
		// rotate();
	}

	public void render(GL2 gl) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		glu.gluLookAt(1.2, 1.0, 2.5, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

		drawAxes(gl);

		gl.glColor3d(1, 1, 1);
		gl.glRotated(rotate, 0, 0, 1);

		glut.glutWireCube(1);
	}

	private void drawAxes(GL2 gl) {
		double x = 1.5, y = 1.5, z = 1.5;
		gl.glLineWidth(4);
		gl.glBegin(GL2.GL_LINES);
		gl.glColor3d(1, 0, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(x, 0, 0);
		gl.glColor3d(0, 1, 0);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, y, 0);
		gl.glColor3d(0, 0, 1);
		gl.glVertex3d(0, 0, 0);
		gl.glVertex3d(0, 0, z);
		gl.glEnd();
		gl.glLineWidth(1);
	}
}
