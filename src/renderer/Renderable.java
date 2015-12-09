/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package renderer;

import com.jogamp.opengl.GL2;
import renderer.primitives.Axes;
import scenegraph.Selectable;

public abstract class Renderable implements IRenderable, Selectable {
    protected final GL2      gl;
    protected final Material material;

    private boolean          selected = false;

    public Renderable(GL2 gl, Material material) {
        this.gl = gl;
        this.material = material;
    }

    @Override
    public void render() {
        if (selected) {
            // Debug axes that don't require lighting
            Axes.renderAxes(gl);
        }

        material.bind();

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
