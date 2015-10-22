package main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Axes;
import renderer.Cube;
import scenegraph.Node;

class Scene {
    private final GL2  gl;
    private final GLU  glu;
    private final GLUT glut;

    private static final double INC_ROTATE = 2;
    private double              rotate     = 0;

    public Scene(GL2 gl) {
        this.gl = gl;
        this.glu = new GLU();
        this.glut = new GLUT();
    }

    public void rotate() {
        rotate += INC_ROTATE;
    }

    public void update() {
        rotate();
    }

    public void render() {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        glu.gluLookAt(1.2, 1.0, 2.5, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);

        // drawAxes(gl);

        gl.glColor3d(1, 1, 1);
        // gl.glRotated(rotate, 0, 0, 1);

        Node cubeNode = new Node(gl);
        cubeNode.setRotation(new Vector3(0, 0, 1), 45.0f);
        cubeNode.attachRenderable(new Cube(gl, glut, 0.5f));

        Node cubeSpin = new Node(gl);
        cubeSpin.setRotation(new Vector3(0, 1, 0), (float) rotate);
        cubeSpin.setPosition(new Vector3(0, 1, 0));
        cubeSpin.attachRenderable(cubeNode);

        Node majorCube = new Node(gl);
        majorCube.attachRenderable(cubeSpin);
        majorCube.attachRenderable(new Cube(gl, glut, 1.0f));
        majorCube.setRotation(new Vector3(0, 0, 1), (float) rotate);

        Node sceneGraph = new Node(gl);
        sceneGraph.attachRenderable(new Axes(gl));
        sceneGraph.attachRenderable(majorCube);

        sceneGraph.render();

        // glut.glutWireCube(1);
    }
}