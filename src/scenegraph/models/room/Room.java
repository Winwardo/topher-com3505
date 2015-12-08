package scenegraph.models.room;

import java.util.ArrayList;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import animation.Animation;
import animation.Keyframe;
import lighting.Light;
import lighting.Lights;
import lighting.PointLight;
import lighting.SpotLight;
import math.Vector3;
import renderer.Materials;
import renderer.cameras.Cameras;
import renderer.cameras.FromPointCamera;
import renderer.primitives.Cuboid;
import renderer.primitives.Plane;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;
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

    public static final int      DEPTH        = 50;
    public static final int      WIDTH        = 35;
    public static final int      HEIGHT       = 12;

    private final SpotLight      robotLight;
    private final SceneGraphNode robotLightNode;

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
        addFloorTrim();
        addStruts();
        addTables();
        addTV();

        // Lights
        Lights lights = Lights.get();

        final Light mainLight1 = new PointLight(
            gl,
            lights.newLightId(),
            new Vector3(2.5f, 2.25f, 2.0f));
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

        makeRoboAnimation();

    }

    private void addFloorTrim() {
        float trimHeight = 1;
        float trimDepth = 0.2f;

        float halfTrimDepth = trimDepth / 2;

        final Cuboid longTrim = new Cuboid(
            gl,
            new Vector3(DEPTH, trimHeight, trimDepth),
            Materials.get().get("wood"));

        final Cuboid shortTrim = new Cuboid(
            gl,
            new Vector3(WIDTH, trimHeight, trimDepth),
            Materials.get().get("wood"));

        root
            .createAttachedNode()
            .attachRenderable(longTrim)
            .setPosition(new Vector3(DEPTH / 2, trimHeight / 2, halfTrimDepth))
            .setRotation(new Vector3(0, 1, 0), 0);

        realtime = root
            .createAttachedNode()
            .attachRenderable(shortTrim)
            .setPosition(
                new Vector3(halfTrimDepth, trimHeight / 2, WIDTH / 2 + 0.5f))
            .setRotation(new Vector3(0, 1, 0), 90);

        root
            .createAttachedNode()
            .attachRenderable(longTrim)
            .setPosition(
                new Vector3(DEPTH / 2, trimHeight / 2, WIDTH - halfTrimDepth))
            .setRotation(new Vector3(0, 1, 0), 180);

        root
            .createAttachedNode()
            .attachRenderable(shortTrim)
            .setPosition(
                new Vector3(
                    DEPTH - halfTrimDepth,
                    trimHeight / 2,
                    WIDTH / 2 + 0.5f))
            .setRotation(new Vector3(0, 1, 0), -90);
    }

    private void addStruts() {
        float strutDepth = 0.5f;
        float strutWidth = 2;

        // Depth
        root
            .createAttachedNode()
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(DEPTH, strutDepth, strutWidth),
                    Materials.get().get("dullmetal")))
            .setPosition(new Vector3(DEPTH / 2, HEIGHT - strutDepth / 2, 0));
        root
            .createAttachedNode()
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(DEPTH, strutDepth, strutWidth),
                    Materials.get().get("dullmetal")))
            .setPosition(
                new Vector3(
                    DEPTH / 2,
                    HEIGHT - strutDepth / 2,
                    WIDTH * (1 / 3.0f)));
        root
            .createAttachedNode()
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(DEPTH, strutDepth, strutWidth),
                    Materials.get().get("dullmetal")))
            .setPosition(
                new Vector3(
                    DEPTH / 2,
                    HEIGHT - strutDepth / 2,
                    WIDTH * (2 / 3.0f)));
        root
            .createAttachedNode()
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(DEPTH, strutDepth, strutWidth),
                    Materials.get().get("dullmetal")))
            .setPosition(
                new Vector3(DEPTH / 2, HEIGHT - strutDepth / 2, WIDTH));

        // Ends
        root
            .createAttachedNode()
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(strutWidth, strutDepth, WIDTH),
                    Materials.get().get("dullmetal")))
            .setPosition(
                new Vector3(DEPTH, HEIGHT - strutDepth / 2, WIDTH / 2));

        root
            .createAttachedNode()
            .attachRenderable(
                new Cuboid(
                    gl,
                    new Vector3(strutWidth, strutDepth, WIDTH),
                    Materials.get().get("dullmetal")))
            .setPosition(new Vector3(0, HEIGHT - strutDepth / 2, WIDTH / 2));
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
            .setPosition(new Vector3(DEPTH - 1, 2, 10))
            .setScaling(new Vector3(15, 7.5f, 1))
            .setRotation(new Vector3(0, 1, 0), 270)
            .attachRenderable(
                new Plane(gl, Materials.get().get("tvscreen"), 1, 1));
    }

    private void addFloorAndCeiling() {
        // Floor
        root
            .createAttachedNode()
            .attachRenderable(
                new Plane(gl, Materials.get().get("marble"), 4, 4))
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
    }

    private void addWalls() {
        float wallBottomHeight = HEIGHT * 0.3f;
        float wallTopHeight = HEIGHT - wallBottomHeight;

        {
            root
                .createAttachedNode()
                .setScaling(new Vector3(DEPTH, wallBottomHeight, 1))
                .attachRenderable(
                    new Plane(gl, Materials.get().get("wood2"), 2, 1));
            root
                .createAttachedNode()
                .setScaling(new Vector3(DEPTH, wallTopHeight, 1))
                .setPosition(new Vector3(0, wallBottomHeight, 0))
                .attachRenderable(
                    new Plane(gl, Materials.get().get("redplastic"), 2, 1));
        }

        root
            .createAttachedNode()
            .setScaling(new Vector3(WIDTH, HEIGHT, 1))
            .setRotation(new Vector3(0, 1, 0), 90)
            .setPosition(new Vector3(0, 0, WIDTH))
            .attachRenderable(
                new Plane(gl, Materials.get().get("redplastic"), 2, 1));

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
                robotPosition.y() + 2.6f,
                robotPosition.z()));
        robotLight.setPosition(new Vector3(0, 0, 0));

        robotLight.setHorizontalRotation(robotNode.rotationAmount() + 90);
        robotLight.setCutoff(30);
        // robotLight.setIncline(45);

        // realtime.setPosition(new Vector3(0, 0, WIDTH));
        // realtime.setRotation(new Vector3(1, 0, 0), -20);

        float currentRotate = robotNode.rotationAmount();
        Vector3 currentPosition = robotNode.position();

        float rotateDifference = currentRotate - lastRotate;
        float speed = currentPosition.distance(lastPosition);

        tiltNode.setRotation(new Vector3(1, 0, 0), rotateDifference * 4);
        leanNode.setRotation(new Vector3(0, 0, 1), -speed * 100);

        lastRotate = currentRotate;
        lastPosition = currentPosition;

        // realtime.setPosition(new Vector3(0.5f, 0, WIDTH / 2 + 0.5f));
        // realtime.setScaling(new Vector3(DEPTH, 2, 2));

    }
}
