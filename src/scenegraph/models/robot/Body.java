package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.primitives.Cylinder;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Body extends SceneGraph {
    private float rotate = 0;

    public Body(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        BallJoint rollerBallJoint = new BallJoint(root);

        root
            .createAttachedNode()
            .attachRenderable(new Cylinder(gl, glut, 0.5f, 1.4f))
            .setRotation(new Vector3(0, 1, 0), -90f)
            .setScaling(new Vector3(1.5f, 1, 1));

        root.createAttachedNodeFromSceneGraph(new Television(gl, glut));

    }

    @Override
    public void update() {
        rotate += 1;
        float ro = (float) Math.sin(rotate / 50);
        float p = ro * 7;

        root.setRotationAmount(90 + p);
    }
}
