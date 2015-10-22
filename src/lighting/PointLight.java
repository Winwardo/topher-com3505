package lighting;

import com.jogamp.opengl.GL2;

public class PointLight extends Light {
    public PointLight(GL2 gl) {
        super(gl);
    }

    @Override
    public void applyImpl() {
        int index = GL2.GL_LIGHT0;

        gl.glEnable(index);

        float[] position = { 0, 0, 0, 1 };
        float[] ambient = { 0.1f, 0.1f, 0.1f, 1.0f };
        float[] diffuse = { 1, 1, 1, 1.0f };
        float[] specular = { 0.3f, 0.3f, 0.3f, 1.0f };

        gl.glLightfv(index, GL2.GL_POSITION, position, 0);
        gl.glLightfv(index, GL2.GL_AMBIENT, ambient, 0);
        gl.glLightfv(index, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glLightfv(index, GL2.GL_SPECULAR, specular, 0);

        // gl.glLightf(index, GL2.GL_SPOT_CUTOFF, angle);
        // gl.glLightfv(index, GL2.GL_SPOT_DIRECTION, direction, 0);
    }
}
