package renderer;

import com.jogamp.opengl.GL2;
import renderer.primitives.Axes;

public abstract class Renderable implements IRenderable {
    protected final GL2 gl;

    public Renderable(GL2 gl) {
        this.gl = gl;
    }

    @Override
    public void render() {
        Axes.renderAxes(gl);
        renderImpl();
    }

    protected abstract void renderImpl();
}
