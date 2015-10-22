package scenegraph;

import java.util.ArrayList;
import java.util.List;
import com.jogamp.opengl.GL2;
import math.Vector3;
import renderer.IRenderable;

public class SceneGraphNode implements IRenderable {
    private List<IRenderable> children;
    private Vector3           localPosition;
    private Vector3           localRotationAngle;
    private float             localRotationAmount;
    private Vector3           localScaling;
    private GL2               gl;

    public SceneGraphNode(List<IRenderable> children, Vector3 localPosition,
        Vector3 localRotation, float localRotationAmount, Vector3 localScaling,
        GL2 gl) {
        super();
        this.children = children;
        this.localPosition = localPosition;
        this.localRotationAngle = localRotation;
        this.localRotationAmount = localRotationAmount;
        this.localScaling = localScaling;
        this.gl = gl;
    }

    public SceneGraphNode(GL2 gl) {
        this(
            new ArrayList<>(),
            Vector3.zero(),
            Vector3.zero(),
            0.0f,
            Vector3.one(),
            gl);
    }

    @Override
    public void render() {
        gl.glPushMatrix();
        {
            translate();
            rotate();
            scale();

            for (IRenderable child : children) {
                child.render();
            }
        }
        gl.glPopMatrix();
    }

    public void attachRenderable(IRenderable renderable) {
        this.children.add(renderable);
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

    @Override
    public List<IRenderable> children() {
        return children;
    }

}
