package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.primitives.Cylinder;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Body extends SceneGraph {
    private float rotate = 0;

    public Body(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));

        root.setRotation(new Vector3(1, 0, 0), 90f);
        root.setScaling(new Vector3(1, 1.5f, 1));
        root.attachRenderable(new Cylinder(gl, glut, 0.5f, 1.4f));
    }

    @Override
    public void update() {
        rotate += 1;
        float ro = (float) Math.sin(rotate / 50);
        float p = ro * 7;

        root.setRotationAmount(90 + p);
    }
}
