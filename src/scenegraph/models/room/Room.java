/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph.models.room;

import com.jogamp.opengl.GL2;
import animation.Animation;
import animation.Animations;
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
import scenegraph.models.HangingLight;
import scenegraph.models.PlateWithGlasses;
import scenegraph.models.Table;
import scenegraph.models.robot.Robot;
import scenegraph.models.robot.Roller;

/**
 * The Room scenegraph architects a bunch of nodes together and gives them life,
 * by applying animations and simulation through the update() method.
 * 
 * @author Topher
 *
 */
public class Room extends SceneGraph {
    public static final int    ROOM_DEPTH   = 50;
    public static final int    ROOM_WIDTH   = 35;
    public static final int    ROOM_HEIGHT  = 12;

    private GL2                gl;

    private SceneGraphNode     robotNode;
    private SceneGraphNode     tiltNode;
    private SceneGraphNode     leanNode;
    private float              lastRotate   = 0;
    private Vector3            lastPosition = Vector3.zero();

    private Robot              robot;

    private Light              robotSpotLight;
    private Light              robotAmbientPointLight;
    private SceneGraphNode     robotLightNode;

    public static HangingLight hangingLight1;
    public static HangingLight hangingLight2;

    public Room(GL2 gl) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        root.setPosition(Vector3.all(0));
        root.setRotation(new Vector3(0, 1, 0), 0);
        root.setScaling(Vector3.one());

        addRobot();
        addFloorAndCeiling();
        addWalls();
        addRoofStruts();
        addTables();
        addTV();
        addLights();
    }

    private void addRobot() {
        robotNode = root
            .createAttachedNode()
            .setPosition(new Vector3(20, 0, 10))
            .setRotation(new Vector3(0, 1, 0), 0);

        leanNode = robotNode.createAttachedNode();
        tiltNode = leanNode
            .createAttachedNodeFromSceneGraph(robot = new Robot(gl));
    }

    private void addLights() {
        Lights lights = Lights.get();

        addHangingLights(lights);
        addWorldLights(lights);
        addRobotLights(lights);
    }

    private void addRobotLights(Lights lights) {
        // Robot lights have a blueish tinge to them
        robotSpotLight = lights
            .addSpotLight(gl, new Vector3(0.8f, 0.8f, 1.0f), 2, 45);
        robotAmbientPointLight = lights
            .addPointLight(gl, new Vector3(1f, 0.7f, 0.7f), 0.2f);

        robotLightNode = root
            .createAttachedNode()
            .attachLight(robotSpotLight)
            .attachLight(robotAmbientPointLight)
            .setPosition(new Vector3(15, 10, 2));
    }

    private void addHangingLights(Lights lights) {
        hangingLight1 = addHangingLight(lights, new Vector3(35, 9, 12));
        hangingLight2 = addHangingLight(lights, new Vector3(10, 9, 12));
    }

    private HangingLight addHangingLight(Lights lights, Vector3 position) {
        // Hanging lights are warm and orangey, with their faked
        // radiosity bounce being even more red as it hits the rugs

        final Vector3 hangingLightColour = new Vector3(1.0f, 0.9f, 0.8f);
        final Vector3 hangingLightFakeReflectionColour = new Vector3(
            1.0f,
            0.6f,
            0.5f);
        final float hangingLightsBrightness = 0.65f;

        HangingLight newLight = new HangingLight(
            gl,
            lights.addSpotLight(
                gl,
                hangingLightColour,
                hangingLightsBrightness,
                45),
            lights.addPointLight(
                gl,
                hangingLightFakeReflectionColour,
                hangingLightsBrightness / 4.0f));
        newLight.spotlight().setPointAt(new float[] { 0, -1, 0, 1 });

        root
            .createAttachedNode()
            .setPosition(position)
            .createAttachedNodeFromSceneGraph(newLight);
        return newLight;
    }

    private void addWorldLights(Lights lights) {
        root
            .createAttachedNode()
            .attachLight(lights.addPointLight(gl, Vector3.one(), 0.35f))
            .setPosition(
                new Vector3(
                    ROOM_DEPTH * (1 / 3.0f),
                    ROOM_HEIGHT / 2,
                    ROOM_WIDTH / 2));

        root
            .createAttachedNode()
            .attachLight(lights.addPointLight(gl, Vector3.one(), 0.35f))
            .setPosition(
                new Vector3(
                    ROOM_DEPTH * (2 / 3.0f),
                    ROOM_HEIGHT / 2,
                    ROOM_WIDTH / 2));
    }

    private void addRoofStruts() {
        float strutWidth = 2;
        float strutDepth = 0.5f;

        final int halfRoomDepth = ROOM_DEPTH / 2;
        final float strutHeight = ROOM_HEIGHT - strutDepth / 2;

        // Main struts
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
            .setPosition(new Vector3(10, 0.1f, 28))
            .setScaling(new Vector3(12, 20, 1));

        root
            .createAttachedNode()
            .attachRenderable(
                new Plane(gl, Materials.get().get("rug"), 32, 32, 1, 1))
            .setRotation(new Vector3(1, 0, 0), 270)
            .setPosition(new Vector3(24, 0.1f, 28))
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
            .createAttachedNodeFromSceneGraph(new Table(gl))
            .setPosition(new Vector3(10, 0, 10))
            .setRotation(new Vector3(0, 1, 0), 45)
            .setScaling(Vector3.all(3));

        root
            .createAttachedNodeFromSceneGraph(new Table(gl))
            .setPosition(new Vector3(37, 0, 7))
            .setRotation(new Vector3(0, 1, 0), -20)
            .setScaling(Vector3.all(3));

        root
            .createAttachedNodeFromSceneGraph(new PlateWithGlasses(gl))
            .setPosition(new Vector3(37, 2.5f, 7));

        root
            .createAttachedNodeFromSceneGraph(new Table(gl))
            .setPosition(new Vector3(13, 0, 28))
            .setRotation(new Vector3(0, 1, 0), -18)
            .setScaling(Vector3.all(3));

        root
            .createAttachedNodeFromSceneGraph(new Table(gl))
            .setPosition(new Vector3(35, 1.5f, 20))
            .setRotation(new Vector3(1, 0, 0), 89)
            .setScaling(Vector3.all(3));
    }

    @Override
    public void update() {
        if (!Animations.get().isPaused()) {
            robot.update();

            applyPhysics();
            applyAnimations();

            Vector3 robotPosition = robotNode.position();
            updateRobotCamera(robotPosition);
            updateRobotLights(robotPosition);
        }
    }

    private void updateRobotLights(Vector3 robotPosition) {
        final float sin = (float) (Math
            .sin(Math.toRadians(robotNode.rotationAmount() + 90)));
        final float cos = (float) (Math
            .cos(Math.toRadians(robotNode.rotationAmount() + 90)));

        final float spotDistance = 1;
        final float ambientDistance = 5;
        final float lightHeight = 2.6f;

        robotLightNode.setPosition(
            new Vector3(
                robotPosition.x() + sin * spotDistance,
                robotPosition.y() + lightHeight,
                robotPosition.z() + cos * spotDistance));

        robotAmbientPointLight.setPosition(
            new Vector3(sin * ambientDistance, 0, cos * ambientDistance));

        robotSpotLight.setHorizontalRotation(robotNode.rotationAmount() + 90);
        robotSpotLight.setCutoff(30);
    }

    private void updateRobotCamera(Vector3 robotPosition) {
        Vector3 cameraAim = new Vector3(
            robotPosition.x(),
            robotPosition.y() + 6f,
            robotPosition.z());

        FromPointCamera robotCamera = (FromPointCamera) Cameras
            .get()
            .get(Cameras.ROBOT_CAMERA);

        robotCamera.setDistance(2);
        robotCamera.setPosition(cameraAim);
        robotCamera.setHeightAngle(0);
        robotCamera.setCircleAngle(-robotNode.rotationAmount());
    }

    private void applyPhysics() {
        // Things like wobbling when going around a corner, and the roller on
        // the bottom actually spinning
        final int tiltAmount = 5;
        final int leanAmount = 100;
        final int rollerRotate = 35;

        float currentRotate = robotNode.rotationAmount();
        Vector3 currentPosition = robotNode.position();

        float rotateDifference = currentRotate - lastRotate;
        float speed = currentPosition.distance(lastPosition);

        tiltNode
            .setRotation(new Vector3(1, 0, 0), rotateDifference * tiltAmount);
        leanNode.setRotation(new Vector3(0, 0, 1), -speed * leanAmount);

        lastRotate = currentRotate;
        lastPosition = currentPosition;

        Roller.rotation += speed * rollerRotate;
    }

    private void applyAnimations() {
        // Preferably these would be got via a name, and not an id that we
        // seemingly have to guess
        Animation robotMovement = Animations.get().get(0);
        Animation armServing = Animations.get().get(1);
        Animation clawServing = Animations.get().get(2);

        robotMovement.applyInterpolated(robotNode);
        armServing.applyInterpolated(Robot.RIGHT_ARM);
        clawServing.applyInterpolated(Robot.RIGHT_CLAW);
    }
}
