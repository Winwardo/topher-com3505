package scenegraph.models.room;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import scenegraph.BallJoint;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;
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

        root.createAttachedNodeFromSceneGraph(robot1 = new Robot(gl, glut));
    }

    @Override
    public void update() {
        robot1.update();
    }
}
