package renderer;

import com.jogamp.opengl.GL2;

public abstract class Renderable implements IRenderable {
    protected final GL2      gl;
    protected final Material mat;

    public Renderable(GL2 gl, Material mat) {
        this.gl = gl;
        this.mat = mat;
    }

    @Override
    public void render() {
        // Axes.renderAxes(gl);
        mat.apply();
        renderImpl();
    }

    protected abstract void renderImpl();
}
