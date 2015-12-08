package scenegraph.models.room;

import java.util.ArrayList;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import animation.Animation;
import animation.Keyframe;
import lighting.Light;
import lighting.Lights;
import math.Vector3;
import renderer.Materials;
import renderer.cameras.Cameras;
import renderer.cameras.FromPointCamera;
import renderer.primitives.Cuboid;
import renderer.primitives.Plane;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;
import scenegraph.models.CircleLamp;
import scenegraph.models.Table;
import scenegraph.models.robot.Robot;

public class Room extends SceneGraph {
    private GL2                  gl;
    private GLUT                 glut;

    private final SceneGraphNode robotNode;
    private final SceneGraphNode tiltNode;
    private final SceneGraphNode leanNode;
    private float                lastRotate   = 0;
    private Vector3              lastPosition = Vector3.zero();
    private SceneGraphNode       realtime;
    private final Robot          robot1;

    private int                  go           = 0;

    public static final int      ROOM_DEPTH   = 50;
    public static final int      ROOM_WIDTH   = 35;
    public static final int      ROOM_HEIGHT  = 12;

    private Light                robotLight;
    private SceneGraphNode       robotLightNode;

    private Animation            robotMovement;

    public Room(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        root.setPosition(Vector3.all(0));
        root.setRotation(new Vector3(0, 1, 0), 0);
        root.setScaling(Vector3.one());

        robotNode = root
            .createAttachedNode()
            .setPosition(new Vector3(20, 0, 10))
            .setRotation(new Vector3(0, 1, 0), 0);

        // realtime = null;

        leanNode = robotNode.createAttachedNode();
        tiltNode = leanNode
            .createAttachedNodeFromSceneGraph(robot1 = new Robot(gl, glut));

        addFloorAndCeiling();
        addWalls();
        addStruts();
        addTables();
        addTV();
        addLights();

        makeRoboAnimation();

    }

    private void addLights() {
        Lights lights = Lights.get();

        if (true) {
            SceneGraphNode circleLamp1 = root
                .createAttachedNode()
                .setPosition(new Vector3(35, 9, 12));

            circleLamp1.createAttachedNodeFromSceneGraph(
                new CircleLamp(
                    gl,
                    glut,
                    lights
                        .addPointLight(gl, new Vector3(1.0f, 0.9f, 0.8f), 0)));
            // 0.65f)));

            SceneGraphNode circleLamp2 = root
                .createAttachedNode()
                .setPosition(new Vector3(15, 9, 12));

            circleLamp2.createAttachedNodeFromSceneGraph(
                new CircleLamp(
                    gl,
                    glut,
                    lights
                        .addPointLight(gl, new Vector3(1.0f, 0.9f, 0.8f), 0)));
            // 0.65f)));
        }

        robotLight = lights
            .addSpotLight(gl, new Vector3(0.8f, 0.8f, 1.0f), 2, 45);
        Light robotLight2 = lights
            .addPointLight(gl, new Vector3(0.8f, 0.8f, 1.0f), 0.2f);

        robotLightNode = root
            .createAttachedNode()
            .attachLight(robotLight)
            .attachLight(robotLight2)
            .setPosition(new Vector3(15, 10, 2));
    }

    private void addStruts() {
        float strutWidth = 2;
        float strutDepth = 0.5f;

        final int halfRoomDepth = ROOM_DEPTH / 2;
        final float strutHeight = ROOM_HEIGHT - strutDepth / 2;

        makeStrut(ROOM_DEPTH, strutWidth)
            .setPosition(new Vector3(halfRoomDepth, strutHeight, 0));

        makeStrut(ROOM_DEPTH, strutWidth).setPosition(
            new Vector3(halfRoomDepth, strutHeight, ROOM_WIDTH * (1 / 3.0f)));

        makeStrut(ROOM_DEPTH, strutWidth).setPosition(
            new Vector3(halfRoomDepth, strutHeight, ROOM_WIDTH * (2 / 3.0f)));

        makeStrut(ROOM_DEPTH, strutWidth)
            .setPosition(new Vector3(halfRoomDepth, strutHeight, ROOM_WIDTH));

        // Ends
        makeStrut(ROOM_WIDTH, strutWidth)
            .setPosition(new Vector3(ROOM_DEPTH, strutHeight, ROOM_WIDTH / 2))
            .setRotation(new Vector3(0, 1, 0), 90);

        makeStrut(ROOM_WIDTH, strutWidth)
            .setPosition(new Vector3(0, strutHeight, ROOM_WIDTH / 2))
            .setRotation(new Vector3(0, 1, 0), 90);
    }

    private SceneGraphNode makeStrut(float strutLength, float strutWidth) {
        float strutDepth = 0.5f;

        return root.createAttachedNode().attachRenderable(
            new Cuboid(
                gl,
                new Vector3(strutLength, strutDepth, strutWidth),
                Materials.get().get("dullmetal"),
                32,
                32,
                32,
                16,
                0.5f,
                1));
    }

    private void makeRoboAnimation() {
        ArrayList<Keyframe> frames = new ArrayList<>();
        frames.add(new Keyframe(new int[] { 25, 0, 4, -90, 100 }));
        frames.add(new Keyframe(new int[] { 25, 0, 4, -140, 100 }));
        frames.add(new Keyframe(new int[] { 14, 0, 12, -140, 100 }));
        frames.add(new Keyframe(new int[] { 14, 0, 12, -200, 100 }));
        frames.add(new Keyframe(new int[] { 14, 0, 12, -90, 100 }));
        frames.add(new Keyframe(new int[] { 15, 0, 25, -80, 100 }));
        frames.add(new Keyframe(new int[] { 15, 0, 25, -80, 100 }));
        frames.add(new Keyframe(new int[] { 15, 0, 25, 10, 100 }));
        frames.add(new Keyframe(new int[] { 30, 0, 23, 10, 100 }));
        frames.add(new Keyframe(new int[] { 30, 0, 23, -60, 100 }));
        frames.add(new Keyframe(new int[] { 32, 0, 26, 0, 100 }));
        frames.add(new Keyframe(new int[] { 41, 0, 25, 40, 100 }));
        frames.add(new Keyframe(new int[] { 41, 0, 24, 90, 100 }));
        frames.add(new Keyframe(new int[] { 41, 0, 13, 100, 100 }));
        frames.add(new Keyframe(new int[] { 41, 0, 13, 138, 100 }));
        frames.add(new Keyframe(new int[] { 35, 0, 10, 140, 100 }));
        frames.add(new Keyframe(new int[] { 35, 0, 10, 90, 100 }));
        frames.add(new Keyframe(new int[] { 35, 0, 10, 170, 100 }));
        frames.add(new Keyframe(new int[] { 25, 0, 8, 150, 100 }));
        frames.add(new Keyframe(new int[] { 25, 0, 7, 90, 100 }));
        frames.add(new Keyframe(new int[] { 25, 0, 4, 90, 100 }));
        robotMovement = new Animation(frames);
    }

    private void addTV() {
        root
            .createAttachedNode()
            .setPosition(new Vector3(ROOM_DEPTH - 0.75f, 1.75f, 9f))
            .setScaling(new Vector3(17, 8.5f, 1))
            .setRotation(new Vector3(0, 1, 0), 270)
            .attachRenderable(new Plane(gl, Materials.get().get("black")));

        root
            .createAttachedNode()
            .setPosition(new Vector3(ROOM_DEPTH - 1, 2.25f, 10))
            .setScaling(new Vector3(15, 7.5f, 1))
            .setRotation(new Vector3(0, 1, 0), 270)
            .attachRenderable(new Plane(gl, Materials.get().get("tvscreen")));
    }

    private void addFloorAndCeiling() {
        // Floor
        root
            .createAttachedNode()
            .attachRenderable(
                new Plane(gl, Materials.get().get("marble"), 32, 32, 4, 4))
            .setRotation(new Vector3(1, 0, 0), 270)
            .setPosition(new Vector3(0, 0, ROOM_WIDTH))
            .setScaling(new Vector3(ROOM_DEPTH, ROOM_WIDTH, 1));

        // Rugs
        root
            .createAttachedNode()
            .attachRenderable(
                new Plane(gl, Materials.get().get("rug"), 32, 32, 1, 1))
            .setRotation(new Vector3(1, 0, 0), 270)
            .setPosition(new Vector3(10, 0.5f, 28))
            .setScaling(new Vector3(12, 20, 1));

        realtime = root
            .createAttachedNode()
            .attachRenderable(
                new Plane(gl, Materials.get().get("rug"), 32, 32, 1, 1))
            .setRotation(new Vector3(1, 0, 0), 270)
            .setPosition(new Vector3(24, 0.5f, 28))
            .setScaling(new Vector3(12, 20, 1));

        // Ceiling
        root
            .createAttachedNode()
            .attachRenderable(
                new Plane(gl, Materials.get().get("wood"), 32, 32, 4, 4))
            .setRotation(new Vector3(1, 0, 0), 90)
            .setPosition(new Vector3(0, ROOM_HEIGHT, 0))
            .setScaling(new Vector3(ROOM_DEPTH, ROOM_WIDTH, 1));
    }

    private void addWalls() {
        SceneGraphNode walls = root.createAttachedNode();

        walls.createAttachedNodeFromSceneGraph(
            new Wall(gl, ROOM_HEIGHT, ROOM_DEPTH));

        walls
            .createAttachedNodeFromSceneGraph(
                new Wall(gl, ROOM_HEIGHT, ROOM_WIDTH))
            .setRotation(new Vector3(0, 1, 0), 90)
            .setPosition(new Vector3(0, 0, ROOM_WIDTH));

        walls
            .createAttachedNodeFromSceneGraph(
                new Wall(gl, ROOM_HEIGHT, ROOM_DEPTH))
            .setRotation(new Vector3(0, 1, 0), 180)
            .setPosition(new Vector3(ROOM_DEPTH, 0, ROOM_WIDTH));

        walls
            .createAttachedNodeFromSceneGraph(
                new Wall(gl, ROOM_HEIGHT, ROOM_WIDTH))
            .setRotation(new Vector3(0, 1, 0), 270)
            .setPosition(new Vector3(ROOM_DEPTH, 0, 0));
    }

    private void addTables() {
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
            .setPosition(new Vector3(13, 0, 28))
            .setRotation(new Vector3(0, 1, 0), -18)
            .setScaling(Vector3.all(3));

        root
            .createAttachedNodeFromSceneGraph(new Table(gl, glut))
            .setPosition(new Vector3(35, 1.5f, 20))
            .setRotation(new Vector3(1, 0, 0), 89)
            .setScaling(Vector3.all(3));
    }

    @Override
    public void update() {
        robotMovement.tick();

        go++;
        robot1.update();

        float point = (float) Math.sin(go);

        FromPointCamera c = (FromPointCamera) Cameras
            .get()
            .get(Cameras.ROBOT_CAMERA);

        robotNode.setPosition(new Vector3(35, 0, 8));
        robotNode.setRotation(new Vector3(0, 1, 0), 60);
        robotMovement.applyInterpolated(robotNode);
        // System.out.println(robotNode.scaling());

        Vector3 robotPosition = robotNode.position();

        Vector3 cameraAim = new Vector3(
            robotPosition.x(),
            robotPosition.y() + 6f,
            robotPosition.z());

        // c.setLookAt(cameraAim);
        c.setDistance(2);
        c.setPosition(cameraAim);
        c.setHeightAngle(0);
        c.setCircleAngle(-robotNode.rotationAmount());

        robotLightNode.setPosition(
            new Vector3(
                robotPosition.x(),
                robotPosition.y() + 2.6f,
                robotPosition.z()));
        // robotLight.setPosition(new Vector3(0, 0, 0));

        robotLight.setHorizontalRotation(robotNode.rotationAmount() + 90);
        robotLight.setCutoff(30);
        // robotLight.setIncline(45);

        // realtime.setPosition(new Vector3(0, 0, WIDTH));
        // realtime.setRotation(new Vector3(1, 0, 0), -20);

        float currentRotate = robotNode.rotationAmount();
        Vector3 currentPosition = robotNode.position();

        float rotateDifference = currentRotate - lastRotate;
        float speed = currentPosition.distance(lastPosition);

        tiltNode.setRotation(new Vector3(1, 0, 0), rotateDifference * 5);
        leanNode.setRotation(new Vector3(0, 0, 1), -speed * 100);

        lastRotate = currentRotate;
        lastPosition = currentPosition;

        // realtime.setPosition(new Vector3(35, 9, 12));
        // realtime.setScaling(new Vector3(DEPTH, 2, 2));

        float strutDepth = 0.5f;
        float strutWidth = 2;

        // realtime.setPosition(new Vector3(30, 0.5f, 28)).setScaling(
        // new Vector3(14, 20, 1));

        // realtime
        // .setPosition(
        // new Vector3(
        // ROOM_DEPTH,
        // ROOM_HEIGHT - strutDepth / 2,
        // ROOM_WIDTH / 2))
        // .setRotation(new Vector3(0, 1, 0), 90);
    }
}
