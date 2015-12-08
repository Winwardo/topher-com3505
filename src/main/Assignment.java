package main;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import lighting.Lights;
import renderer.cameras.Cameras;
import renderer.cameras.RotateAroundPointCamera;
import scenegraph.Selectable;

public class Assignment extends JFrame implements GLEventListener,
    ActionListener, ChangeListener, MouseMotionListener {

    private static final long  serialVersionUID  = 1L;

    private static final float FIELD_OF_VIEW     = 90.0f;
    private static final int   WIDTH             = 640;
    private static final int   HEIGHT            = 480;
    private static final int   SCALING           = 2;
    private static final float NEAR_CLIP         = 1f;
    private static final float FAR_CLIP          = 100.0f;
    private static final int   FRAMES_PER_SECOND = 60;

    private Scene              scene;

    private Point              mouseLastLocation;

    public static void main(String[] args) {
        Assignment t1 = new Assignment();
        t1.setVisible(true);
    }

    public Assignment() {
        super("Topher's 3D assignment");
        setSize(WIDTH * SCALING, HEIGHT * SCALING);

        GLCanvas canvas = makeGLCanvas();
        add(canvas, "Center");
        canvas.addMouseMotionListener(this);

        setupExitEvents(canvas);

        createAndStartAnimation(canvas);
    }

    private void createAndStartAnimation(GLCanvas canvas) {
        FPSAnimator animator = new FPSAnimator(canvas, FRAMES_PER_SECOND);
        animator.start();
    }

    private GLCanvas makeGLCanvas() {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        caps.setNumSamples(4);
        caps.setSampleBuffers(true);
        return new GLCanvas(caps);
    }

    private void setupUI(Scene scene) {
        addMenuBar();
        addShaderSlider();
        addSceneGraphTree(scene);
        addLightsSelection();
    }

    private void addLightsSelection() {
        Panel p = new Panel();

        JCheckBox light1 = new JCheckBox("Light #1");
        JCheckBox light2 = new JCheckBox("Light #2");
        JCheckBox lightRobot = new JCheckBox("Robot light");

        p.add(light1);
        p.add(light2);
        p.add(lightRobot);

        light1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lights.get().get(1).enable(light1.isSelected());
            }
        });
        light2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lights.get().get(0).enable(light2.isSelected());
            }
        });
        lightRobot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Lights.get().get(2).enable(lightRobot.isSelected());
                Lights.get().get(3).enable(lightRobot.isSelected());
            }
        });

        light1.setSelected(true);
        light2.setSelected(true);
        lightRobot.setSelected(true);

        this.add(p, "South");
    }

    private void addMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem quitItem = new MenuItem("Quit");

        quitItem.addActionListener(this);
        fileMenu.add(quitItem);

        menuBar.add(fileMenu);

        this.setMenuBar(menuBar);
    }

    private void addShaderSlider() {
        Panel p = new Panel();

        JSlider zoomSlider = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
        zoomSlider.setName("ShaderSlider");
        zoomSlider.setMajorTickSpacing(1);
        zoomSlider.setPaintTicks(true);
        zoomSlider.setPaintLabels(true);
        zoomSlider.setValue(5);
        zoomSlider.addChangeListener(this);

        p.add(zoomSlider);
        this.add(p, "North");
    }

    private void addSceneGraphTree(Scene scene) {
        Panel p = new Panel();

        JTree sceneGraphJTree = makeSceneGraphJTree(scene);
        JScrollPane scrollPane = new JScrollPane(sceneGraphJTree);

        p.add(scrollPane);
        this.add(p, "West");
    }

    private JTree makeSceneGraphJTree(Scene scene) {
        DefaultMutableTreeNode result = scene
            .sceneGraph()
            .createSceneGraphTree();

        JTree sceneGraphJTree = new JTree(result);

        sceneGraphJTree.setShowsRootHandles(true);
        sceneGraphJTree.setRootVisible(false);

        sceneGraphJTree.addTreeSelectionListener(new TreeSelectionListener() {
            private Selectable lastSelected = null;

            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) sceneGraphJTree
                    .getLastSelectedPathComponent();

                // Deselect the last object
                if (lastSelected != null) {
                    lastSelected.setSelected(false);
                }
                lastSelected = ((Selectable) (selectedNode.getUserObject()));
                lastSelected.setSelected(true);
            }
        });

        return sceneGraphJTree;
    }

    private void setupExitEvents(GLCanvas canvas) {
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

        scene = new Scene(gl);

        setupUI(scene);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
        int height) {
        GL2 gl = drawable.getGL().getGL2();

        float fAspect = (float) width / height;
        float fovy = FIELD_OF_VIEW;

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

    @Override
    public void stateChanged(ChangeEvent e) {
        Object source = e.getSource();
        if (source instanceof JSlider) {
            JSlider slider = (JSlider) source;
            switch (slider.getName()) {
                case "ShaderSlider":
                    scene.setShader(slider.getValue());
                    break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        Point mouseLocation = event.getPoint();

        float dx = (float) (mouseLocation.x - mouseLastLocation.x) / WIDTH;
        float dy = (float) (mouseLocation.y - mouseLastLocation.y) / HEIGHT;

        RotateAroundPointCamera camera = (RotateAroundPointCamera) Cameras
            .get()
            .get(Cameras.MAIN_CAMERA);

        if (event.getModifiers() == MouseEvent.BUTTON1_MASK) {
            camera.addRotation(dx, dy);
        } else if (event.getModifiers() == MouseEvent.BUTTON3_MASK) {
            camera.addDistance(dy * 10);
        }

        mouseLastLocation = mouseLocation;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseLastLocation = e.getPoint();
    }
}
