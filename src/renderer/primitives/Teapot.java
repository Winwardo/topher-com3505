package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Renderable;
import renderer.Material;

public class Teapot extends Renderable {
    private GLUT          glut;
    private final Material tex;

    public Teapot(GL2 gl, GLUT glut, Material tex) {
        super(gl);
        this.glut = glut;
        this.tex = tex;
    }

    @Override
    public void renderImpl() {
        tex.apply();
        glut.glutSolidTeapot(1);
    }
}
