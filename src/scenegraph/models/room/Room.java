package scenegraph.models.room;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.Light;
import lighting.Lights;
import lighting.PointLight;
import lighting.SpotLight;
import math.Vector3;
import renderer.Materials;
import renderer.cameras.Cameras;
import renderer.cameras.FromPointCamera;
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

    private int                  go     = 0;

    public static final int      DEPTH  = 50;
    public static final int      WIDTH  = 35;
    public static final int      HEIGHT = 12;

    private final SpotLight      robotLight;
    private final SceneGraphNode robotLightNode;

    public Room(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        root.setPosition(Vector3.all(0));
        root.setRotation(new Vector3(0, 1, 0), 0);
        root.setScaling(Vector3.one());

        robotNode = root
            .createAttachedNodeFromSceneGraph(robot1 = new Robot(gl, glut))
            .setPosition(new Vector3(20, 0, 10))
            .setRotation(new Vector3(0, 0, 0), 0);

        // Floor
        realtime = root
            .createAttachedNode()
            .attachRenderable(
                new Plane(gl, Materials.get().get("marbletile"), 4, 4))
            .setRotation(new Vector3(1, 0, 0), 270)
            .setPosition(new Vector3(0, 0, WIDTH))
            .setScaling(new Vector3(DEPTH, WIDTH, 1));

        // Ceiling
        root
            .createAttachedNode()
            .attachRenderable(new Plane(gl, Materials.get().get("wood"), 4, 4))
            .setRotation(new Vector3(1, 0, 0), 90)
            .setPosition(new Vector3(0, HEIGHT, 0))
            .setScaling(new Vector3(DEPTH, WIDTH, 1));

        // Walls
        root
            .createAttachedNode()
            .setScaling(new Vector3(DEPTH, HEIGHT, 1))
            .attachRenderable(new Plane(gl, Materials.get().get("wall"), 2, 1));

        root
            .createAttachedNode()
            .setScaling(new Vector3(WIDTH, HEIGHT, 1))
            .setRotation(new Vector3(0, 1, 0), 90)
            .setPosition(new Vector3(0, 0, WIDTH))
            .attachRenderable(new Plane(gl, Materials.get().get("wall"), 2, 1));

        root
            .createAttachedNode()
            .setScaling(new Vector3(DEPTH, HEIGHT, 1))
            .setRotation(new Vector3(0, 1, 0), 180)
            .setPosition(new Vector3(DEPTH, 0, WIDTH))
            .attachRenderable(new Plane(gl, Materials.get().get("wall"), 2, 1));

        root
            .createAttachedNode()
            .setScaling(new Vector3(WIDTH, HEIGHT, 1))
            .setRotation(new Vector3(0, 1, 0), 270)
            .setPosition(new Vector3(DEPTH, 0, 0))
            .attachRenderable(new Plane(gl, Materials.get().get("wall"), 2, 1));

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
            .setPosition(new Vector3(WIDTH, 1.5f, 20))
            .setRotation(new Vector3(1, 0, 0), 89)
            .setScaling(Vector3.all(3));

        // TV on wall

        root
            .createAttachedNode()
            .setPosition(new Vector3(DEPTH - 1, 2, 10))
            .setScaling(new Vector3(15, 7.5f, 1))
            .setRotation(new Vector3(0, 1, 0), 270)
            .attachRenderable(
                new Plane(gl, Materials.get().get("tvscreen"), 1, 1));

        // Lights
        Lights lights = Lights.get();

        final Light mainLight1 = new PointLight(
            gl,
            lights.newLightId(),
            // new Vector3(2.5f, 2.25f, 2.0f));
            new Vector3(0, 0, 0f));
        root.createAttachedNode().attachLight(mainLight1).setPosition(
            new Vector3(35, 10, 2));
        lights.append(mainLight1);

        robotLight = new SpotLight(
            gl,
            lights.newLightId(),
            new Vector3(5.5f, 5.25f, 5.0f));
        robotLightNode = root
            .createAttachedNode()
            .attachLight(robotLight)
            .setPosition(new Vector3(15, 10, 2));

        lights.append(robotLight);
    }

    @Override
    public void update() {
        go++;
        robot1.update();

        float point = (float) Math.sin(go);

        FromPointCamera c = (FromPointCamera) Cameras
            .get()
            .get(Cameras.ROBOT_CAMERA);

        robotNode.setPosition(new Vector3(30, 0, 20));
        robotNode.setRotation(new Vector3(0, 1, 0), go);
        Vector3 robotPosition = robotNode.position();

        Vector3 cameraAim = new Vector3(
            robotPosition.x(),
            robotPosition.y() + 4,
            robotPosition.z());

        // c.setLookAt(cameraAim);
        c.setDistance(2);
        c.setPosition(cameraAim);
        c.setHeightAngle(0);
        c.setCircleAngle(-robotNode.rotationAmount());

        robotLightNode.setPosition(
            new Vector3(
                robotPosition.x(),
                robotPosition.y() + 4,
                robotPosition.z()));
        robotLight.setPosition(new Vector3(0, 0, 0));

        robotLight.setHorizontalRotation(robotNode.rotationAmount() + 90);
        robotLight.setCutoff(30);
        // robotLight.setIncline(45);

        realtime.setPosition(new Vector3(0, 0, WIDTH));
    }
}
