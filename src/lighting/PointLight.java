package lighting;

import com.jogamp.opengl.GL2;
import math.Vector3;

public class PointLight extends Light {
    private float[] color;

    public PointLight(GL2 gl, int lightId, Vector3 color) {
        super(gl, lightId);

        this.color = new float[] { color.x(), color.y(), color.z(), 1.0f };
    }

    public PointLight(GL2 gl, int lightId) {
        this(gl, lightId, new Vector3(1, 1, 1));
    }

    @Override
    public void applyImpl() {
        int index = lightId;

        float[] position = { 0, 0, 0, 1 };
        float[] ambient = { 0.1f, 0.1f, 0.1f, 1.0f };

        // float[] diffuse = { 2f, 1.0f, 1.0f, 1.0f };
        // float[] specular = { 0.7f, 0.9f, 0.7f, 1.0f };

        gl.glLightfv(index, GL2.GL_POSITION, position, 0);
        gl.glLightfv(index, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(index, GL2.GL_DIFFUSE, color, 0);
        gl.glLightfv(index, GL2.GL_SPECULAR, color, 0);

        // gl.glLightf(index, GL2.GL_SPOT_CUTOFF, angle);
        // gl.glLightfv(index, GL2.GL_SPOT_DIRECTION, direction, 0);
    }
}
