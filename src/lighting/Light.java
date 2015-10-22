package lighting;

import com.jogamp.opengl.GL2;

public abstract class Light implements ILight {
    protected final GL2 gl;
    protected final int lightId;

    public Light(GL2 gl, int lightId) {
        this.gl = gl;
        this.lightId = lightId;

        gl.glEnable(lightId);
    }

    @Override
    public void apply() {
        applyImpl();
    }

    public abstract void applyImpl();

}
