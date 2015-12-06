package scenegraph.models.room;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.Lights;
import lighting.PointLight;
import math.Vector3;
import renderer.Materials;
import renderer.cameras.Cameras;
import renderer.cameras.RotateAroundPointCamera;
import renderer.primitives.Plane;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;
import scenegraph.models.Table;
import scenegraph.models.robot.Robot;

public class Room extends SceneGraph {
    private GL2                  gl;
    private GLUT                 glut;

    private final SceneGraphNode robotNode;
    private final SceneGraphNode realtime;
    private final Robot          robot1;

    final int                    roomWidth  = 50;
    final int                    roomDepth  = 35;
    final int                    roomHeight = 12;

    public Room(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        robotNode = root
            .createAttachedNodeFromSceneGraph(robot1 = new Robot(gl, glut))
            .setPosition(new Vector3(0, 0, 0))
            .setRotation(new Vector3(0, 0, 0), 0);

        // Floor
        root
            .createAttachedNode()
            .attachRenderable(
                new Plane(gl, Materials.get().get("marbletile"), 4, 4))
            .setRotation(new Vector3(1, 0, 0), 90)
            .setPosition(new Vector3(0, 0, 0))
            .setScaling(new Vector3(roomWidth, roomDepth, 1));

        // Walls
        root
            .createAttachedNode()
            .attachRenderable(new Plane(gl, Materials.get().get("nyan"), 4, 4))
            .setScaling(new Vector3(roomWidth, roomHeight, 1));

        // Tables
        root
            .createAttachedNodeFromSceneGraph(new Table(gl, glut))
            .setPosition(new Vector3(10, 0, 10))
            .setRotation(new Vector3(0, 1, 0), 45)
            .setScaling(Vector3.all(3));

        root
            .createAttachedNodeFromSceneGraph(new Table(gl, glut))
            .setPosition(new Vector3(37, 0, 7))
            .setRotation(new Vector3(0, 1, 0), -20)
            .setScaling(Vector3.all(3));

        root
            .createAttachedNodeFromSceneGraph(new Table(gl, glut))
            .setPosition(new Vector3(roomDepth, 1.5f, 20))
            .setRotation(new Vector3(1, 0, 0), 89)
            .setScaling(Vector3.all(3));

        // Lights
        Lights lights = Lights.get();
        final PointLight tableLight = new PointLight(
            gl,
            lights.newLightId(),
            Vector3.all(1));
        realtime = root.createAttachedNode().attachLight(tableLight);
        lights.append(tableLight);
    }

    @Override
    public void update() {
        robot1.update();

        RotateAroundPointCamera c = (RotateAroundPointCamera) Cameras
            .get()
            .get(Cameras.ROBOT_CAMERA);

        Vector3 robotPosition = robotNode.position();
        Vector3 cameraAim = new Vector3(
            robotPosition.x(),
            robotPosition.y() + 1.5f,
            robotPosition.z());

        c.setLookAt(cameraAim);
        c.setTargetCircleAngle(-robotNode.rotationAmount() + 180);

        realtime.setPosition(new Vector3(25, 10, 10));
    }
}
