package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Cylinder;
import renderer.Sphere;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Roller extends SceneGraph {

    private GL2  gl;
    private GLUT glut;

    private final SceneGraphNode ballNode;
    private float                rotation = 0;

    public Roller(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        ballNode = new SceneGraphNode(gl);
        ballNode.attachRenderable(new Sphere(gl, glut, 1.5f));
        ballNode.setScaling(new Vector3(0.75f, 0.75f, 1f));

        SceneGraphNode axis = new SceneGraphNode(gl);
        final float rollerLength = 3.25f;
        axis.attachRenderable(new Cylinder(gl, glut, 0.35f, rollerLength));
        axis.setPosition(new Vector3(0, 0, -rollerLength / 2));

        root.attachNode(ballNode);
        root.attachNode(axis);
    }

    @Override
    public void update() {
        rotation += 1.0f;
        ballNode.setRotation(new Vector3(0, 0, -1), rotation);
    }
}
