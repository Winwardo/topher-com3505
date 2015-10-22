package scenegraph;

import java.util.ArrayList;
import java.util.List;
import com.jogamp.opengl.GL2;
import lighting.ILight;
import math.Vector3;
import renderer.IRenderable;

public class SceneGraphNode {
    private List<IRenderable>    renderables;
    private List<SceneGraphNode> nodes;
    private List<ILight>         lights;

    private Vector3 localPosition;
    private Vector3 localRotationAngle;
    private float   localRotationAmount;
    private Vector3 localScaling;
    private GL2     gl;

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

            for (IRenderable renderable : renderables) {
                renderable.render();
            }
            for (SceneGraphNode node : nodes) {
                node.render();
            }
        }
        gl.glPopMatrix();
    }

    public void applyLights() {
        gl.glPushMatrix();
        {
            transform();

            for (ILight light : lights) {
                light.apply();
            }
            for (SceneGraphNode node : nodes) {
                node.applyLights();
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
        this.renderables.add(renderable);
    }

    public void attachNode(SceneGraphNode node) {
        nodes.add(node);
    }

    public void setPosition(Vector3 position) {
        this.localPosition = position;
    }

    public void setRotation(Vector3 angle, float amount) {
        this.localRotationAngle = angle;
        this.localRotationAmount = amount;
    }

    public void setRotationAmount(float amount) {
        this.localRotationAmount = amount;
    }

    public void setScaling(Vector3 scaling) {
        this.localScaling = scaling;
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

}
