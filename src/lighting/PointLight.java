package lighting;

import com.jogamp.opengl.GL2;
import math.Vector3;

public class PointLight extends Light {
    private float[] color;

    public PointLight(GL2 gl, int lightId, Vector3 color, float brightness) {
        super(gl, lightId);

        this.color = new float[] { color.x() * brightness,
            color.y() * brightness, color.z() * brightness, 1.0f };
    }

    public PointLight(GL2 gl, int lightId, Vector3 color) {
        this(gl, lightId, color, 1);
    }

    public PointLight(GL2 gl, int lightId) {
        this(gl, lightId, new Vector3(1, 1, 1));
    }

    @Override
    public void applyImpl() {
        int index = lightId;

        float[] position = { 0, 0, 0, 1 };
        float[] ambient = { 0.1f, 0.1f, 0.1f, 1.0f };

        gl.glLightfv(index, GL2.GL_POSITION, position, 0);
        gl.glLightfv(index, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(index, GL2.GL_DIFFUSE, color, 0);
        gl.glLightfv(index, GL2.GL_SPECULAR, color, 0);

        gl.glLightf(index, GL2.GL_SPOT_CUTOFF, 180);
        // gl.glLightfv(index, GL2.GL_SPOT_DIRECTION, new float[] { 1, 0, 0 },
        // 0);
    }
}
