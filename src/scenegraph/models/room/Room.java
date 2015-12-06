package scenegraph.models.room;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Plane;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;
import scenegraph.models.Table;
import scenegraph.models.robot.Robot;

public class Room extends SceneGraph {
    private static final float ARM_OUT = 0.65f;
    private GL2                gl;
    private GLUT               glut;

    private final BallJoint    rollerBallJoint;
    private final Robot        robot1;

    public Room(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        rollerBallJoint = new BallJoint(root);

        root
            .createAttachedNodeFromSceneGraph(robot1 = new Robot(gl, glut))
            .setPosition(new Vector3(0, 1, 0));

        root
            .createAttachedNode()
            .attachRenderable(new Plane(gl, Materials.get().get("wood"), 4, 4))
            .setRotation(new Vector3(1, 0, 0), 90)
            .setPosition(new Vector3(0, 0, 0))
            .setScaling(new Vector3(50, 50, 1));

        root
            .createAttachedNodeFromSceneGraph(new Table(gl, glut))
            .setPosition(new Vector3(20, 0, 10))
            .setScaling(Vector3.all(2));
    }

    @Override
    public void update() {
        robot1.update();
    }
}
