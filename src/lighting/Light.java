package lighting;

import com.jogamp.opengl.GL2;

public abstract class Light implements ILight {
    protected final GL2 gl;
    protected int       lightId;

    public Light(GL2 gl) {
        this.gl = gl;
    }

    @Override
    public void apply() {
        // Axes.renderAxes(gl);
        applyImpl();
    }

    public abstract void applyImpl();

    public void setLightId(int lightId) {
        this.lightId = lightId;
    }

    public void enable() {
        gl.glEnable(lightId);
    }

    public void disable() {
        gl.glDisable(lightId);
    }
}
