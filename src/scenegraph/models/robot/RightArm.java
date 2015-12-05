package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cylinder;
import renderer.primitives.Sphere;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;
import scenegraph.models.PlateWithGlasses;

public class RightArm extends SceneGraph {
    private static final float   ELBOW_OFFSET     = 0.1f;
    private static final float   UPPER_ARM_LENGTH = 1.0f;
    private static final float   LOWER_ARM_LENGTH = 0.75f;
    private static final float   ARM_THICKNESS    = 0.25f;

    private GL2                  gl;
    private GLUT                 glut;

    private final SceneGraphNode elbowBall;
    private final SceneGraphNode forearm;

    private final BallJoint      shoulderJoint;
    private final BallJoint      elbowJoint;

    private float                rotateShoulder   = 0;
    private float                rotateElbow      = 0;

    public RightArm(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        shoulderJoint = new BallJoint(root);

        shoulderJoint.get().attachRenderable(
            new Cylinder(
                gl,
                glut,
                ARM_THICKNESS,
                UPPER_ARM_LENGTH,
                Materials.get().get("shinymetal")));

        SceneGraphNode elbowOffset = shoulderJoint
            .get()
            .createAttachedNode()
            .setPosition(new Vector3(0, 0, UPPER_ARM_LENGTH + ELBOW_OFFSET));

        elbowJoint = new BallJoint(elbowOffset);

        elbowBall = elbowJoint.get().createAttachedNode();
        elbowBall.attachRenderable(new Sphere(gl, 0.25f));

        forearm = elbowBall.createAttachedNode();
        forearm.attachRenderable(
            new Cylinder(
                gl,
                glut,
                ARM_THICKNESS / 2,
                LOWER_ARM_LENGTH,
                Materials.get().get("shinymetal")));

        forearm
            .createAttachedNodeFromSceneGraph(new PlateWithGlasses(gl, glut))
            .setRotation(new Vector3(1, 0, 0), 90)
            .setPosition(new Vector3(0, 0, LOWER_ARM_LENGTH))
            .setScaling(Vector3.all(0.75f));
    }

    @Override
    public void update() {

        // elbowJoint.setYaw(45);
        elbowJoint.setRoll(-90);

        if (true) {
            return;
        }
        rotateShoulder += 1;
        float ro = (float) Math.sin(rotateShoulder / 50);
        float p = ro * 10;
        shoulderJoint.setRoll(35 + p);

        float rro = (float) Math.sin(rotateShoulder / 76);
        float pp = rro * 20;
        shoulderJoint.setYaw(pp);

        rotateElbow += 1;
        elbowJoint.setYaw(20);
        elbowJoint.setPitch(rotateElbow);
    }
}