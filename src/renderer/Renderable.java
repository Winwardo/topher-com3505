package renderer;

import java.util.Collections;
import java.util.List;
import com.jogamp.opengl.GL2;

public abstract class Renderable implements IRenderable {
    protected final GL2 gl;

    public Renderable(GL2 gl) {
        this.gl = gl;
    }

    @Override
    public List<IRenderable> children() {
        return Collections.emptyList();
    }

    @Override
    public void render() {
        Axes.renderAxes(gl);
        renderImpl();
    }

    abstract void renderImpl();
}
