package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Material;
import renderer.Renderable;

public class Cube extends Renderable {
    private GLUT  glut;
    private float size;

    public Cube(GL2 gl, GLUT glut, Material mat) {
        super(gl, mat);
        this.glut = glut;
    }

    @Override
    public void renderImpl() {
        glut.glutSolidCube(1);
    }
}
