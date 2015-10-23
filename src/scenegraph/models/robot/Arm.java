package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Cylinder;
import renderer.Sphere;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Arm extends SceneGraph {
    private static final float ELBOW_OFFSET     = 0.1f;
    private static final float UPPER_ARM_LENGTH = 1.0f;
    private static final float LOWER_ARM_LENGTH = 0.75f;
    private static final float ARM_THICKNESS    = 0.25f;

    private GL2  gl;
    private GLUT glut;

    private final SceneGraphNode elbowBall;
    private final SceneGraphNode forearm;

    private final BallJoint elbowJoint;
    private float           rotate = 0;

    public Arm(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        root.attachRenderable(
            new Cylinder(gl, glut, ARM_THICKNESS, UPPER_ARM_LENGTH));

        SceneGraphNode elbowOffset = root.createAttachedNode().setPosition(
            new Vector3(0, 0, UPPER_ARM_LENGTH + ELBOW_OFFSET));

        elbowJoint = new BallJoint(gl, glut, elbowOffset);

        elbowBall = elbowJoint.get().createAttachedNode();
        elbowBall.attachRenderable(new Sphere(gl, glut, 0.25f));

        forearm = elbowBall.createAttachedNode();
        forearm.attachRenderable(
            new Cylinder(gl, glut, ARM_THICKNESS / 2, LOWER_ARM_LENGTH));
        // forearm.setPosition(new Vector3(0, 0, ELBOW_OFFSET + 0.1f));
        // forearm.setRotation(new Vector3(1, 0, 0), 45);
    }

    @Override
    public void update() {
        rotate += 1;
        elbowJoint.setYaw(20);
        elbowJoint.setPitch(rotate);
    }
}
