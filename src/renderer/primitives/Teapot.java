package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Material;
import renderer.Renderable;

public class Teapot extends Renderable {
    private GLUT glut;

    public Teapot(GL2 gl, GLUT glut, Material mat) {
        super(gl, mat);
        this.glut = glut;
    }

    @Override
    public void renderImpl() {
        glut.glutSolidTeapot(1);
    }
}
