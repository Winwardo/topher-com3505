package lighting;

import com.jogamp.opengl.GL2;
import renderer.Axes;

public abstract class Light implements ILight {
    protected final GL2 gl;

    public Light(GL2 gl) {
        this.gl = gl;
    }

    @Override
    public void apply() {
        Axes.renderAxes(gl);
        applyImpl();
    }

    public abstract void applyImpl();

}
