package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Sphere;
import renderer.Teapot;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Head extends SceneGraph {
    private static final float eyeOffset = 0.5f;

    private GL2  gl;
    private GLUT glut;

    public Head(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        // Core head
        root.setScaling(new Vector3(0.6f, 1, 1));
        root.attachRenderable(new Teapot(gl, glut));

        root.attachNode(leftEye());
        root.attachNode(rightEye());

    }

    private SceneGraphNode leftEye() {
        return attachEye(-eyeOffset);
    }

    private SceneGraphNode rightEye() {
        return attachEye(eyeOffset);
    }

    public SceneGraphNode attachEye(float xOffset) {
        System.out.println("hi");
        SceneGraphNode eye = new SceneGraphNode(gl);
        eye.attachRenderable(new Sphere(gl, glut));
        eye.setScaling(Vector3.all(0.2f));
        eye.setPosition(new Vector3(0.7f, 0.2f, xOffset));
        return eye;
    }

    @Override
    public void update() {
    }

}
