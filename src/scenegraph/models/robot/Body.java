package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Cylinder;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Body extends SceneGraph {
    private GL2  gl;
    private GLUT glut;

    public Body(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        root.setRotation(new Vector3(1, 0, 0), 90f);
        root.setScaling(new Vector3(1, 1.5f, 1));
        root.attachRenderable(new Cylinder(gl, glut, 0.5f, 1.2f));
    }

    @Override
    public void update() {
    }
}
