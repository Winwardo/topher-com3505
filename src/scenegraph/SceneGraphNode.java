package scenegraph;

import java.util.ArrayList;
import java.util.List;
import com.jogamp.opengl.GL2;
import lighting.ILight;
import math.Vector3;
import renderer.IRenderable;

public class SceneGraphNode {
    private final List<IRenderable>    renderables;
    private final List<SceneGraphNode> nodes;
    private final List<ILight>         lights;

    private Vector3 localPosition;
    private Vector3 localRotationAngle;
    private float   localRotationAmount;
    private Vector3 localScaling;

    private GL2 gl;

    public SceneGraphNode(Vector3 localPosition, Vector3 localRotation,
        float localRotationAmount, Vector3 localScaling, GL2 gl) {
        super();

        this.renderables = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.lights = new ArrayList<>();

        this.localPosition = localPosition;
        this.localRotationAngle = localRotation;
        this.localRotationAmount = localRotationAmount;
        this.localScaling = localScaling;

        this.gl = gl;
    }

    public SceneGraphNode(GL2 gl) {
        this(Vector3.zero(), Vector3.zero(), 0.0f, Vector3.one(), gl);
    }

    public void render() {
        gl.glPushMatrix();
        {
            transform();

            // On the way traversing down the tree, apply all lights, BEFORE any
            // polygons are drawn.
            for (ILight light : lights) {
                light.apply();
            }

            for (SceneGraphNode node : nodes) {
                node.render();
            }

            // And draw all our object with correct lighting after
            for (IRenderable renderable : renderables) {
                renderable.render();
            }
        }
        gl.glPopMatrix();
    }

    private void transform() {
        translate();
        rotate();
        scale();
    }

    public void attachRenderable(IRenderable renderable) {
        renderables.add(renderable);
    }

    public SceneGraphNode createAttachedNode() {
        SceneGraphNode newNode = new SceneGraphNode(gl);
        attachNode(newNode);
        return newNode;
    }

    public SceneGraphNode createAttachedNodeFromSceneGraph(
        SceneGraph sceneGraph) {
        SceneGraphNode node = sceneGraph.root();
        attachNode(node);
        return node;
    }

    public SceneGraphNode attachLight(ILight light) {
        lights.add(light);
        return this;
    }

    public SceneGraphNode setPosition(Vector3 position) {
        this.localPosition = position;
        return this;
    }

    public SceneGraphNode setRotation(Vector3 angle, float amount) {
        this.localRotationAngle = angle;
        this.localRotationAmount = amount;
        return this;
    }

    public SceneGraphNode setRotationAmount(float amount) {
        this.localRotationAmount = amount;
        return this;
    }

    public SceneGraphNode setScaling(Vector3 scaling) {
        this.localScaling = scaling;
        return this;
    }

    private void attachNode(SceneGraphNode node) {
        nodes.add(node);
    }

    private void rotate() {
        gl.glRotatef(
            localRotationAmount,
            localRotationAngle.x(),
            localRotationAngle.y(),
            localRotationAngle.z());
    }

    private void translate() {
        gl.glTranslatef(
            localPosition.x(),
            localPosition.y(),
            localPosition.z());
    }

    private void scale() {
        gl.glScalef(localScaling.x(), localScaling.y(), localScaling.z());
    }

    public List<SceneGraphNode> children() {
        return nodes;
    }

    public List<IRenderable> renderables() {
        return renderables;
    }

    public List<ILight> lights() {
        return lights;
    }

}
