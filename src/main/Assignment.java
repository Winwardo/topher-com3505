/* I declare that this code is my own work. This file was modified from an original file by Steve Maddock. */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package main;

import java.awt.GridLayout;
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
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
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
import animation.Animations;
import lighting.Lights;
import renderer.cameras.Cameras;
import renderer.cameras.RotateAroundPointCamera;
import scenegraph.Selectable;
import scenegraph.models.robot.Robot;
import scenegraph.models.room.Room;

public class Assignment extends JFrame implements GLEventListener,
    ActionListener, ChangeListener, MouseMotionListener {

    private static final float FIELD_OF_VIEW     = 90.0f;
    private static final int   WIDTH             = 1280;
    private static final int   HEIGHT            = 960;
    private static final float NEAR_CLIP         = 1f;
    private static final float FAR_CLIP          = 100.0f;
    private static final int   FRAMES_PER_SECOND = 60;

    public static int          CURRENT_WIDTH     = WIDTH;
    public static int          CURRENT_HEIGHT    = HEIGHT;

    private Scene              scene;
    private Point              mouseLastLocation;

    public static void main(String[] args) {
        Assignment t1 = new Assignment();
        t1.setVisible(true);
    }

    public Assignment() {
        super("Topher's 3D assignment");
        setSize(WIDTH + 400, HEIGHT);

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
        this.add(makeShaderRadioChoice(), "North");

        Panel west = new Panel();
        west.setLayout(new BoxLayout(west, BoxLayout.PAGE_AXIS));
        west.add(makeAnimationControls(), "North");
        west.add(makeSceneGraphTree(scene), "South");
        west.add(makeCameraSelection(), "South");

        Panel south = new Panel();
        south.add(makeLightsSelection(), "North");

        this.add(west, "West");
        this.add(south, "South");
    }

    private Panel makeCameraSelection() {
        Panel p = new Panel();
        ButtonGroup bg = new ButtonGroup();

        final JRadioButton btn_mainCamera = new JRadioButton(
            "Controllable camera");
        final JRadioButton btn_robotCamera = new JRadioButton(
            "Robot view camera");

        bg.add(btn_mainCamera);
        bg.add(btn_robotCamera);

        p.add(btn_mainCamera);
        p.add(btn_robotCamera);

        btn_mainCamera.setSelected(true);

        btn_mainCamera.addActionListener((e) -> {
            Cameras.get().setMainCamera(Cameras.MAIN_CAMERA);
        });
        btn_robotCamera.addActionListener((e) -> {
            Cameras.get().setMainCamera(Cameras.ROBOT_CAMERA);
        });

        return p;
    }

    private Panel makeAnimationControls() {
        Panel p = new Panel();
        // p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setLayout(new GridLayout());

        JButton playAnimation = new JButton("Play animation");
        JButton pauseAnimation = new JButton("Pause animation");
        JButton restartAnimation = new JButton("Restart animation");

        playAnimation.addActionListener((e) -> {
            Animations.get().setPaused(false);
        });
        pauseAnimation.addActionListener((e) -> {
            Animations.get().setPaused(true);
        });
        restartAnimation.addActionListener((e) -> {
            Animations.get().restartAll();
        });

        p.add(playAnimation);
        p.add(pauseAnimation);
        p.add(restartAnimation);

        return p;
    }

    private Panel makeShaderRadioChoice() {
        Panel p = new Panel();
        ButtonGroup bg = new ButtonGroup();

        final JRadioButton btn_noShader = new JRadioButton("No shader");
        final JRadioButton btn_albedo = new JRadioButton("Albedo");
        final JRadioButton btn_ambient = new JRadioButton("Ambient");
        final JRadioButton btn_diffuse = new JRadioButton("Diffuse");
        final JRadioButton btn_specular = new JRadioButton("Specular");
        final JRadioButton btn_blinnPhong = new JRadioButton("Blinn-Phong");

        bg.add(btn_noShader);
        bg.add(btn_albedo);
        bg.add(btn_ambient);
        bg.add(btn_diffuse);
        bg.add(btn_specular);
        bg.add(btn_blinnPhong);

        p.add(btn_noShader);
        p.add(btn_albedo);
        p.add(btn_ambient);
        p.add(btn_diffuse);
        p.add(btn_specular);
        p.add(btn_blinnPhong);

        btn_blinnPhong.setSelected(true);

        btn_noShader.addActionListener((e) -> {
            scene.setShader(0);
        });
        btn_albedo.addActionListener((e) -> {
            scene.setShader(1);
        });
        btn_ambient.addActionListener((e) -> {
            scene.setShader(2);
        });
        btn_diffuse.addActionListener((e) -> {
            scene.setShader(3);
        });
        btn_specular.addActionListener((e) -> {
            scene.setShader(4);
        });
        btn_blinnPhong.addActionListener((e) -> {
            scene.setShader(5);
        });
        return p;
    }

    private Panel makeLightsSelection() {
        Panel p = new Panel();

        JCheckBox worldLights = new JCheckBox("World lights");
        JCheckBox spotlight1 = new JCheckBox("Spotlight #1");
        JCheckBox spotlight2 = new JCheckBox("Spotlight #2");
        JCheckBox lightRobot = new JCheckBox("Robot light");
        JCheckBox radiosityLights = new JCheckBox("Enable fake radiosity");

        p.add(worldLights);
        p.add(spotlight1);
        p.add(spotlight2);
        p.add(lightRobot);
        p.add(radiosityLights);

        AtomicBoolean radiosity = new AtomicBoolean(true);

        worldLights.addActionListener((e) -> {
            Lights.get().get(4).enable(worldLights.isSelected());
            Lights.get().get(5).enable(worldLights.isSelected());
        });

        spotlight1.addActionListener((e) -> {
            Lights.get().get(2).enable(spotlight1.isSelected());
            Lights.get().get(3).enable(spotlight1.isSelected());

            Room.circleLamp2.setIsOn(spotlight1.isSelected());
        });
        spotlight2.addActionListener((e) -> {
            Lights.get().get(0).enable(spotlight2.isSelected());
            Lights.get().get(1).enable(spotlight2.isSelected());
            Room.circleLamp1.setIsOn(spotlight2.isSelected());
        });
        lightRobot.addActionListener((e) -> {
            Lights.get().get(6).enable(lightRobot.isSelected());
            Lights.get().get(7).enable(lightRobot.isSelected());
            Robot.CHEST_LIGHT.setIsOn(lightRobot.isSelected());
        });

        radiosityLights.addActionListener((e) -> {
            Lights.get().get(3).hide(!radiosityLights.isSelected());
            Lights.get().get(1).hide(!radiosityLights.isSelected());
            Lights.get().get(7).hide(!radiosityLights.isSelected());
        });

        worldLights.setSelected(true);
        worldLights.doClick();
        spotlight1.setSelected(true);
        spotlight2.setSelected(true);
        lightRobot.setSelected(true);
        radiosityLights.setSelected(true);

        return p;
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

    private Panel makeSceneGraphTree(Scene scene) {
        Panel p = new Panel();

        JTree sceneGraphJTree = makeSceneGraphJTree(scene);
        JScrollPane scrollPane = new JScrollPane(sceneGraphJTree);

        p.add(scrollPane);
        return p;
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
        Assignment.CURRENT_HEIGHT = height;
        Assignment.CURRENT_WIDTH = width;

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
