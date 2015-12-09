package lighting;

import com.jogamp.opengl.GL2;
import math.Vector3;

/**
 * Constructors are package restricted so only Lights can access them. Lights
 * can correctly set the lightId as required.
 * 
 * @author Topher
 *
 */
public class Light implements ILight {
    private GL2     gl;

    private float[] color;
    private float[] pointAt   = new float[] { 0, 0, -1 };
    private float   cutoff    = 180;
    private Vector3 position;
    private int     lightId;

    private boolean isEnabled = true;
    private boolean isHidden  = false;

    Light(GL2 gl, Vector3 color, float brightness, float cutoff) {
        this.gl = gl;

        this.color = new float[] { color.x() * brightness,
            color.y() * brightness, color.z() * brightness, 1.0f };
        this.position = Vector3.zero();
        this.cutoff = cutoff;
    }

    Light(GL2 gl, Vector3 color, float brightness) {
        this(gl, color, brightness, 180);
    }

    Light(GL2 gl, Vector3 color) {
        this(gl, color, 1);
    }

    Light(GL2 gl) {
        this(gl, new Vector3(1, 1, 1));
    }

    public void setPointAt(float[] pointAt) {
        this.pointAt = pointAt;
    }

    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setHorizontalRotation(float angle) {
        float sin = (float) Math.sin(Math.toRadians(angle));
        float cos = (float) Math.cos(Math.toRadians(angle));

        pointAt[0] = sin;
        pointAt[2] = cos;
    }

    public void setIncline(float angle) {
        float sin = (float) Math.sin(Math.toRadians(angle));
        pointAt[1] = sin;
    }

    @Override
    public void apply() {
        // Axes.renderAxes(gl);

        int index = lightId;
        float[] position1 = { position.x(), position.y(), position.z(), 1f };
        float[] ambient = { 0.1f, 0.1f, 0.1f, 1.0f };

        gl.glLightfv(index, GL2.GL_POSITION, position1, 0);

        if (isEnabled && !isHidden) {
            gl.glLightfv(index, GL2.GL_AMBIENT, ambient, 0);
            gl.glLightfv(index, GL2.GL_DIFFUSE, color, 0);
            gl.glLightfv(index, GL2.GL_SPECULAR, color, 0);
        } else {
            float[] disabled = new float[] { 0, 0, 0, 0 };
            gl.glLightfv(index, GL2.GL_AMBIENT, disabled, 0);
            gl.glLightfv(index, GL2.GL_DIFFUSE, disabled, 0);
            gl.glLightfv(index, GL2.GL_SPECULAR, disabled, 0);
        }

        gl.glLightf(index, GL2.GL_SPOT_CUTOFF, cutoff);
        gl.glLightfv(index, GL2.GL_SPOT_DIRECTION, pointAt, 0);
    }

    public void setLightId(int lightId) {
        this.lightId = lightId;
    }

    public void enable() {
        gl.glEnable(lightId);
    }

    public void disable() {
        gl.glDisable(lightId);
    }

    @Override
    public void enable(boolean enabled) {
        isEnabled = enabled;
        if (enabled) {
            enable();
        } else {
            disable();
        }
    }

    @Override
    public void hide(boolean hidden) {
        isHidden = hidden;
    }
}
