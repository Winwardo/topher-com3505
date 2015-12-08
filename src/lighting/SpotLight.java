package lighting;

import com.jogamp.opengl.GL2;
import math.Vector3;

public class SpotLight extends Light {
    private float[] color;
    private float[] pointAt = new float[] { 0, 0, -1 };
    private float   cutoff  = 45;
    private Vector3 position;

    public SpotLight(GL2 gl, Vector3 color, float brightness, float cutoff) {
        super(gl);

        this.color = new float[] { color.x() * brightness,
            color.y() * brightness, color.z() * brightness, 1.0f };
        this.position = Vector3.zero();
        this.cutoff = cutoff;
    }

    public SpotLight(GL2 gl, Vector3 color, float brightness) {
        this(gl, color, brightness, 180);
    }

    public SpotLight(GL2 gl, Vector3 color) {
        this(gl, color, 1);
    }

    public SpotLight(GL2 gl) {
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

    @Override
    public void applyImpl() {
        int index = lightId;
        float[] position1 = { position.x(), position.y(), position.z(), 1f };
        float[] ambient = { 0.1f, 0.1f, 0.1f, 1.0f };

        gl.glLightfv(index, GL2.GL_POSITION, position1, 0);
        gl.glLightfv(index, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(index, GL2.GL_DIFFUSE, color, 0);
        gl.glLightfv(index, GL2.GL_SPECULAR, color, 0);

        // float[] direction = new float[] { 0, -10, 10 };

        gl.glLightf(index, GL2.GL_SPOT_CUTOFF, cutoff);
        gl.glLightfv(index, GL2.GL_SPOT_DIRECTION, pointAt, 0);
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
}
