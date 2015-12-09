package renderer;

import com.jogamp.opengl.GL2;
import renderer.primitives.Axes;
import scenegraph.Selectable;

public abstract class Renderable implements IRenderable, Selectable {
    protected final GL2      gl;
    protected final Material mat;

    private boolean          selected = false;

    public Renderable(GL2 gl, Material mat) {
        this.gl = gl;
        this.mat = mat;
    }

    @Override
    public void render() {
        if (selected) {
            Axes.renderAxes(gl);
        }

        mat.bind();

        gl.glPushMatrix();
        renderImpl();
        gl.glPopMatrix();
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    protected abstract void renderImpl();
}
