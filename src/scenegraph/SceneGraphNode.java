/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph;

import java.util.ArrayList;
import java.util.List;
import com.jogamp.opengl.GL2;
import lighting.Light;
import math.Vector3;
import renderer.IRenderable;
import renderer.Selectable;
import renderer.primitives.Axes;

/**
 * Contains a list of lights, renderables, and other nodes for traversing. Nodes
 * rendered through it keep their local transforms; in this way it is easy to
 * build complex models with translations, rotations and scaling.
 * 
 * Most setters on this class (setPosition etc) return this, and as such are
 * chainable in the following fashion:
 * 
 * <pre>
 * SceneGraphNode node = root.createAttachedNode().setPosition(position).setRotation(rotation)
 * </pre>
 * 
 * @author Topher
 *
 */
public class SceneGraphNode implements Selectable {
    private final List<IRenderable>    renderables;
    private final List<SceneGraphNode> nodes;
    private final List<Light>          lights;

    private Vector3                    localPosition;
    private Vector3                    localRotationAngle;
    private float                      localRotationAmount;
    private Vector3                    localScaling;

    private GL2                        gl;

    private boolean                    selected = false;
    private boolean                    hidden   = false;

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

    /**
     * Recursively apply all lights in this node and children nodes, with
     * appropriate transforms applied.
     */
    public void applyLights() {
        gl.glPushMatrix();
        {
            transform();

            for (Light light : lights) {
                light.apply();
            }

            for (SceneGraphNode node : nodes) {
                node.applyLights();
            }
        }
        gl.glPopMatrix();
    }

    /**
     * Recursively render this node, and all children nodes, with appropriate
     * transforms applied.
     */
    public void render() {
        if (hidden) {
            return;
        }

        gl.glPushMatrix();
        {
            transform();

            if (selected) {
                Axes.renderAxes(gl);
            }

            for (IRenderable renderable : renderables) {
                renderable.render();
            }

            for (SceneGraphNode node : nodes) {
                node.render();
            }
        }
        gl.glPopMatrix();
    }

    private void transform() {
        translate();
        rotate();
        scale();
    }

    public SceneGraphNode attachRenderable(IRenderable renderable) {
        renderables.add(renderable);
        return this;
    }

    /**
     * Make a new child node, and attach it to this one, available for immediate
     * usage.
     * 
     */
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

    public SceneGraphNode attachLight(Light light) {
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

    public Vector3 rotation() {
        return this.localRotationAngle;
    }

    public float rotationAmount() {
        return this.localRotationAmount;
    }

    public Vector3 position() {
        return this.localPosition;
    }

    public Vector3 scaling() {
        return this.localScaling;
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

    public List<Light> lights() {
        return lights;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void hide() {
        hidden = true;
    }

    public void unhide() {
        hidden = false;
    }
}
