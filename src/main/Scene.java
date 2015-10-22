package main;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Axes;
import renderer.Cube;
import renderer.IRenderable;
import scenegraph.Node;
import scenegraph.SceneGraph;

class Scene {
    private final GL2  gl;
    private final GLU  glu;
    private final GLUT glut;

    private final SceneGraph sceneGraph;

    private Node majorCube;
    private Node minorCubeSpin;

    private static final double INC_ROTATE = 2;
    private double              rotate     = 0;

    public Scene(GL2 gl) {
        this.gl = gl;
        this.glu = new GLU();
        this.glut = new GLUT();

        setupGL(gl);

        sceneGraph = makeSceneGraph();
    }

    private SceneGraph makeSceneGraph() {
        Node cubeNode = new Node(gl);
        cubeNode.setRotation(new Vector3(0, 0, 1), 45.0f);
        cubeNode.attachRenderable(new Cube(gl, glut, 0.5f));

        minorCubeSpin = new Node(gl);
        minorCubeSpin.setPosition(new Vector3(0, 1, 0));
        minorCubeSpin.attachRenderable(cubeNode);

        majorCube = new Node(gl);
        majorCube.attachRenderable(minorCubeSpin);
        majorCube.attachRenderable(new Cube(gl, glut, 1.0f));

        Node root = new Node(gl);
        root.attachRenderable(new Axes(gl));
        root.attachRenderable(majorCube);

        return new SceneGraph(root);
    }

    private void setupGL(GL2 gl) {
        gl.glClearColor(0, 0, 0, 1);
        gl.glEnable(GL.GL_DEPTH_TEST);
        // gl.glEnable(GL2.GL_LIGHTING);
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glMaterialfv(
            GL.GL_FRONT,
            GL2.GL_AMBIENT_AND_DIFFUSE,
            new float[] { 0.2f, 0.2f, 0.2f, 1.0f },
            0);
    }

    public void rotate() {
        rotate += INC_ROTATE;
    }

    public void update() {
        rotate();

        minorCubeSpin.setRotation(new Vector3(0, 1, 0), (float) rotate);
        majorCube.setRotation(new Vector3(0, 0, 1), (float) rotate);
    }

    public void render() {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        glu.gluLookAt(1.2, 1.0, 2.5, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

        gl.glColor3d(1, 1, 1);

        sceneGraph.render();
    }

    public void updateSceneGraphTree(DefaultMutableTreeNode sceneGraphTree) {
        sceneGraphTree.removeAllChildren();
        sceneGraphTree.add(getCurrentAndChildren(sceneGraph.root()));
    }

    private MutableTreeNode getCurrentAndChildren(IRenderable renderable) {
        DefaultMutableTreeNode result = new DefaultMutableTreeNode(renderable);

        for (IRenderable child : renderable.children()) {
            result.add(getCurrentAndChildren(child));
        }

        return result;
    }
}